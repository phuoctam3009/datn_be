package com.example.finalproject.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.finalproject.entity.Resume;

public interface ResumeRepository extends PagingAndSortingRepository<Resume, Integer> {

}
