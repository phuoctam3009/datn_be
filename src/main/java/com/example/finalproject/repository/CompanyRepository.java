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
	@Query("SELECT c FROM Company c JOIN User u on c.user.id = u.id and u.deleted = false ")
	public Page<Company> findAllItem(Pageable pageable);
	@Query("SELECT c FROM Company c JOIN User u on c.user.id = u.id and u.deleted = false and c.isActive = true")
	public Page<Company> findAllItemActive(Pageable pageable);
	
	@Modifying
	@Transactional
	@Query("UPDATE Company c SET c.isActive = ?2 WHERE c.id = ?1")
	public int updateStatus(Integer recruitmentId, boolean active);
	
	@Query("SELECT c FROM Company c where c.user.id = ?1")
	public Company getCompanyByUserId(Integer userId);

}