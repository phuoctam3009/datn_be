package com.example.finalproject.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.entity.City;
import com.example.finalproject.repository.CityRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")

@RestController
@RequestMapping(path = "/city")
public class CityController{
	@Autowired
	private CityRepository repository;

	@GetMapping(path = "/getAll")
	public ResponseEntity getAllCity() {
		List<City> findAll = repository.findAll();
		return ResponseEntity.ok(findAll);
	}
}
