package com.example.finalproject.api;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.dto.RequestPage;
import com.example.finalproject.entity.Recruitment;
import com.example.finalproject.repository.RecruitmentRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/recruitment") // This means URL's start with /demo (after Application path)
public class RecruitmentController {
	@Autowired
	private RecruitmentRepository recruitmentRepository;

	@GetMapping(path = "/getAll")
	public ResponseEntity getAllRecruitments(@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "size", required = true) int size) {
		// This returns a JSON or XML with the users
		Page<Recruitment> findAll = recruitmentRepository.findAll(PageRequest.of(page - 1, size));
		return ResponseEntity.ok(findAll);
	}

	@GetMapping("/{id}")
	public Recruitment getRecruitmentById(@PathVariable(name = "id") Integer id) {
		System.out.println("id: " + id);
//		recruitmentRepository.
		Optional<Recruitment> findById = recruitmentRepository.findById(id);
		Recruitment recruitment = findById.get();
		return recruitment;
	}
}
