package com.example.finalproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.finalproject.entity.Company;
import com.example.finalproject.entity.Recruitment;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface CompanyRepository extends CrudRepository<Company, Integer> {
	@Query("SELECT c FROM Company c")
	public Page<Company> findAllItem(Pageable pageable);

}