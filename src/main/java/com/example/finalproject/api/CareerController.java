package com.example.finalproject.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.entity.Career;
import com.example.finalproject.repository.CareerRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")

@RestController
@RequestMapping(path = "/career")
public class CareerController {
	@Autowired
	private CareerRepository repository;

	@GetMapping(path = "/getAll")
	public ResponseEntity getAllRecruitments() {
		List<Career> findAll = repository.findAll();
		return ResponseEntity.ok(findAll);
	}
}
