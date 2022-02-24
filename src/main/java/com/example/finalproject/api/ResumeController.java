package com.example.finalproject.api;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.cloudinary.CloudinaryService;
import com.example.finalproject.dto.ResumeDto;
import com.example.finalproject.entity.Candidate;
import com.example.finalproject.entity.Company;
import com.example.finalproject.entity.Resume;
import com.example.finalproject.payload.response.CustomErrorResponse;
import com.example.finalproject.payload.response.MessageResponse;
import com.example.finalproject.repository.CandidateRepository;
import com.example.finalproject.repository.ResumeRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/resume")
public class ResumeController {
	@Autowired
	private ResumeRepository resumeRepository;
	@Autowired
	private CandidateRepository canditeRepository;
	@Autowired
	private CloudinaryService cloudService;

	@PostMapping("/addResume")
	@ResponseBody
	public ResponseEntity addResume(@ModelAttribute ResumeDto data) throws IOException {
		Resume resume = new Resume();
		byte[] decodedBytes = DatatypeConverter
				.parseBase64Binary(data.getFile().replace("data:image/jpeg;base64,", "").trim());
//		Base64.getDecoder().decode
		File file = new File("D:/PhuocTam/file/test.jpeg");
		FileUtils.writeByteArrayToFile(file, decodedBytes);
//        File file = new File("D:/PhuocTam/file/test.jpg");
//        Path write = Files.write(file.toPath(), decode);
		String uploadFile = cloudService.uploadFile(file);
		file.delete();
		Candidate findByUserId = canditeRepository.findByUserId(Integer.parseInt(data.getUserId()));
		resume.setCandidate(findByUserId);
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

	@GetMapping("/getResume/{userId}")
	public ResponseEntity listResume(@PathVariable("userId") Integer userId) {
		List<Resume> resumesByUserId = resumeRepository.getResumesByUserId(userId);
		return ResponseEntity.ok(resumesByUserId);
	}

	@GetMapping("/{id}")
	public Resume getResumeById(@PathVariable(name = "id") Integer id) {
		Resume resume = resumeRepository.findById(id).get();
		return resume;
	}
	
	@DeleteMapping(path = "/delete/{id}")
	public ResponseEntity deleleResume(@PathVariable("id") Integer id) {
		resumeRepository.deleteById(id);
		return ResponseEntity.ok("Xóa CV thành công!");
	}
}
