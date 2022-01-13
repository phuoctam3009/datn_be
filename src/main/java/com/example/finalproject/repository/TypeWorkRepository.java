package com.example.finalproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.finalproject.entity.TypeWork;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface TypeWorkRepository extends CrudRepository<TypeWork, Integer> {

}