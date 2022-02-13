package com.example.finalproject.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.entity.Company;
import com.example.finalproject.repository.CompanyRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/company")
public class CompanyController {
	@Autowired
	private CompanyRepository companyRepository;

	@GetMapping("/{id}")
	public Company getCompanyById(@PathVariable(name = "id") Integer id) {
		Company company = companyRepository.findById(id).get();
		return company;
	}
}
