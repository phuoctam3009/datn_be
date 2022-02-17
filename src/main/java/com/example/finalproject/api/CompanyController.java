package com.example.finalproject.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@GetMapping("/getAll")
	public ResponseEntity getAllCompany(@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "size", required = true) int size) {
		Page<Company> findAllItem = companyRepository.findAllItem(PageRequest.of(page - 1, size));
		return ResponseEntity.ok(findAllItem);
	}

	@PutMapping("/status")
	public ResponseEntity updateStatusCompany(@RequestParam(value = "status", required = true) boolean status,
			@RequestParam(value = "id", required = true) int id) {
		companyRepository.updateStatus(id, status);
		return ResponseEntity.ok("Update trạng thái thành công");

	}
}
