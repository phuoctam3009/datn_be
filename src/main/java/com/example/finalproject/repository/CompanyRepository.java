package com.example.finalproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.finalproject.entity.Company;
import com.example.finalproject.entity.Recruitment;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface CompanyRepository extends JpaRepository<Company, Integer> {
	@Query("SELECT c FROM Company c")
	public Page<Company> findAllItem(Pageable pageable);
	
	@Modifying
	@Transactional
	@Query("UPDATE Company c SET c.isActive = ?2 WHERE c.id = ?1")
	public int updateStatus(Integer recruitmentId, boolean active);

}