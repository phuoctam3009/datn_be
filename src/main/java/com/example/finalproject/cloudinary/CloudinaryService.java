package com.example.finalproject.cloudinary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryService {

	@Autowired
	private CloudinaryConfig cloudinaryConfig;

	private Cloudinary cloudinary;

	@PostConstruct
	public void connectCloudinary() {
		Map config = new HashMap();
		config.put("cloud_name", cloudinaryConfig.getCloudName());
		config.put("api_key", cloudinaryConfig.getApiKey());
		config.put("api_secret", cloudinaryConfig.getApiSecret());
		this.cloudinary = new Cloudinary(config);
	}

//	public String uploadFile(MultipartFile file) {
//		try {
//			File uploadedFile = convertMultiPartToFile(file);
//			Map uploadResult = cloudinary.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
//			return uploadResult.get("url").toString();
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}

	public String uploadFile(Object file) {
		try {
			Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
			return uploadResult.get("url").toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

}
