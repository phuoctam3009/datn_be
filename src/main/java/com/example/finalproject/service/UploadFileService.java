package com.example.finalproject.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {
	@Autowired
	private Environment env;
	
	public String uploadingFile(MultipartFile file) {
	    String uploadingDir = env.getProperty("upload.basePath");
	    File fileDir = new File(uploadingDir);
	    if(!fileDir.exists()) {
	    	fileDir.mkdir();
	    }
//		String fileName = "test.pdf";
		String fileName = new SimpleDateFormat("yyyyMMddHHmm'.pdf'").format(new Date());

//		File fileStore = new File(uploadingDir + fileName);
		Path saveTO = Paths.get(uploadingDir + fileName);
		try {
			Files.copy(file.getInputStream(), saveTO);
		} catch (Exception e) {
			System.out.println("Lỗi lưu file: " + e.toString());
		}
		return saveTO.getFileName().toString();
		
	}
}
