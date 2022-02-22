package com.example.finalproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.finalproject.entity.Candidate;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface CandidateRepository extends JpaRepository<Candidate, Integer> {
	@Override
	@Query("SELECT c from Candidate c JOIN User u on c.user.id = u.id and u.deleted = false")
	public Page<Candidate> findAll(Pageable pageable);
	
	@Query("SELECT c FROM Candidate c join User u on c.user.id = u.id and u.deleted = false and u.id = ?1")
	public Candidate findByUserId(Integer userId);

}