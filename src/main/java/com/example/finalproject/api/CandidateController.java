package com.example.finalproject.api;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.cloudinary.CloudinaryService;
import com.example.finalproject.entity.Candidate;
import com.example.finalproject.entity.Company;
import com.example.finalproject.entity.User;
import com.example.finalproject.payload.response.MessageResponse;
import com.example.finalproject.repository.CandidateRepository;
import com.example.finalproject.repository.UserRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/candidate")
public class CandidateController {
	@Autowired
	private CandidateRepository candidateRepository;
	@Autowired
	private CloudinaryService cloudService;
	@Autowired
	private UserRepository userRepository;

	@GetMapping(path = "/getAll")
	public ResponseEntity<Page> getAllRecruitments(@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "size", required = true) int size) {
		// This returns a JSON or XML with the users
//		Page<Recruitment> findAll = recruitmentRepository.findAll(PageRequest.of(page - 1, size));
		Page<Candidate> findAll = candidateRepository.findAll(PageRequest.of(page - 1, size));
		return ResponseEntity.ok(findAll);
	}

	@GetMapping("/{id}")
	public Candidate getCandidateById(@PathVariable(name = "id") Integer id) {
		Candidate candidate = candidateRepository.findByUserId(id);
		return candidate;
	}
	
	@PutMapping("/updateProfile")
	public ResponseEntity updateProfileCandidate(@RequestBody Candidate candidate) {
		Candidate save = candidateRepository.save(candidate);
		return ResponseEntity.ok("Cập nhật thông tin cá nhân thành công");
	}
	
	@PutMapping("/updateAvatar")
	@ResponseBody
	public ResponseEntity updateAvatarCandidate(@RequestParam("filePath") String filePath, @RequestParam("userId") Integer userId) {
		byte[] decodedBytes = DatatypeConverter
				.parseBase64Binary(filePath.replace("data:image/png;base64,", "").trim());
		File file = new File("D:/PhuocTam/file/test.png");
		try {
			FileUtils.writeByteArrayToFile(file, decodedBytes);
		} catch (IOException e) {
			System.out.println("Lỗi ghi file: " + e.toString());
			return ResponseEntity.badRequest().body(new MessageResponse("Có lỗi trong quá trình upload file!"));
		}
//        File file = new File("D:/PhuocTam/file/test.jpg");
//        Path write = Files.write(file.toPath(), decode);
		String uploadFile = cloudService.uploadFile(file);
		Candidate findByUserId = candidateRepository.findByUserId(userId);
		findByUserId.setAvatar(uploadFile);
		candidateRepository.save(findByUserId);
		return ResponseEntity.ok("Cập nhật avatar thành công!");
		
	}

}
