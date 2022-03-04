package com.example.finalproject.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.entity.Level;
import com.example.finalproject.repository.LevelRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/level")
public class LevelController {
	@Autowired
	private LevelRepository levelRepository;

	@GetMapping(path = "/getAll")
	public ResponseEntity getAllLevels() {
		List<Level> findAll = levelRepository.findAll();
		return ResponseEntity.ok(findAll);
	}
}
