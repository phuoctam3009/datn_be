package com.example.finalproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.finalproject.entity.Company;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface CompanyRepository extends CrudRepository<Company, Integer> {

}