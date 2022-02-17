package com.example.finalproject.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.entity.Candidate;
import com.example.finalproject.repository.CandidateRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/candidate")
public class CandidateController {
	@Autowired
	private CandidateRepository candidateRepository;
	
	@GetMapping(path = "/getAll")
	public ResponseEntity<Page> getAllRecruitments(@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "size", required = true) int size) {
		// This returns a JSON or XML with the users
//		Page<Recruitment> findAll = recruitmentRepository.findAll(PageRequest.of(page - 1, size));
		Page<Candidate> findAll = candidateRepository.findAll(PageRequest.of(page - 1, size));
		return ResponseEntity.ok(findAll);
	}

}
