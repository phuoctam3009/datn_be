package com.example.finalproject.api;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

	@PostMapping("/addResume")
	@ResponseBody
	public ResponseEntity addResume(@ModelAttribute ResumeDto data) {
		System.out.println(data);
		Resume resume = new Resume();
		resume.setCandidateId(Integer.parseInt(data.getUserId()));
		resume.setContent(data.getData());
		resume.setTitle(data.getTitle());
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
