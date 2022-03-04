package com.example.finalproject.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.entity.TypeWork;
import com.example.finalproject.repository.TypeWorkRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/typework")
public class TypeWorkController {
	@Autowired
	private TypeWorkRepository repository;

	@GetMapping(path = "/getAll")
	public ResponseEntity getAllTypeWork() {
		List<TypeWork> findAll = repository.findAll();
		return ResponseEntity.ok(findAll);
	}
}
