package com.example.finalproject.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Base64;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.cloudinary.CloudinaryService;
import com.example.finalproject.dto.ResumeDto;
import com.example.finalproject.entity.Resume;
import com.example.finalproject.payload.response.CustomErrorResponse;
import com.example.finalproject.repository.ResumeRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/resume")
public class ResumeController {
	@Autowired
	private ResumeRepository resumeRepository;
	@Autowired
	private CloudinaryService cloudService;

	@PostMapping("/addResume")
	@ResponseBody
	public ResponseEntity addResume(@ModelAttribute ResumeDto data) throws IOException {
		Resume resume = new Resume();
		byte[] decodedBytes = DatatypeConverter.parseBase64Binary(data.getFile().replace("data:image/jpeg;base64,", "").trim());
//		Base64.getDecoder().decode
		File file = new File("D:/PhuocTam/file/test.jpeg");
		FileUtils.writeByteArrayToFile(file, decodedBytes);
//        File file = new File("D:/PhuocTam/file/test.jpg");
//        Path write = Files.write(file.toPath(), decode);
		String uploadFile = cloudService.uploadFile(file);
		file.delete();
		resume.setCandidateId(Integer.parseInt(data.getUserId()));
		resume.setContent(data.getData());
		resume.setTitle(data.getTitle());
		resume.setAvatar(uploadFile);
		Resume save = resumeRepository.save(resume);
		if (save != null && save.getContent() != null) {
//		if (!true) {
			return ResponseEntity.ok("Lưu hồ sơ thành công");
		} else {
			CustomErrorResponse errors = new CustomErrorResponse();
			errors.setTimestamp(LocalDateTime.now());
			errors.setError("Lưu hồ sơ thất bại");
			errors.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
