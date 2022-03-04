package com.example.finalproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.finalproject.entity.Candidate;
import com.example.finalproject.entity.Resume;

public interface ResumeRepository extends PagingAndSortingRepository<Resume, Integer> {
	@Query("SELECT r FROM Resume r JOIN User u on r.candidate.user.id = u.id and u.deleted = false and u.id = ?1")
	public List<Resume> getResumesByUserId(Integer userId);
	
	@Query("SELECT r FROM Resume r inner JOIN r.recruitments re where re.id = ?1")
	public List<Resume> getResumesByRecruitmentId(Integer recruitmentId);
}
