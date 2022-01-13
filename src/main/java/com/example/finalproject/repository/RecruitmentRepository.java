package com.example.finalproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.finalproject.entity.Recruitment;

@Repository
public interface RecruitmentRepository extends PagingAndSortingRepository<Recruitment, Integer> {
	@Query("SELECT r FROM Recruitment r")
	public List<Recruitment> findAllItem();
}
