package com.example.finalproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.finalproject.dto.CompanyDto;
import com.example.finalproject.entity.Company;
import com.example.finalproject.entity.Recruitment;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface CompanyRepository extends JpaRepository<Company, Integer> {
	@Query("SELECT c FROM Company c JOIN User u on c.user.id = u.id and u.deleted = false ")
	public Page<Company> findAllItem(Pageable pageable);

	@Query("SELECT c FROM Company c JOIN User u on c.user.id = u.id and u.deleted = false and c.isActive = true")
	public Page<Company> findAllItemActive(Pageable pageable);

	@Query(value = "Select c.id, c.avatar, c.background, c.description, c.company_name as companyName from company c " + "where c.is_active = true and ("
			+ "(?1 is null or lower(c.address) like lower(concat('%', ?1, '%'))) "
			+ "or (?1 is null or lower(c.company_name) like lower(concat('%', ?1, '%'))) "
			+ "or (?1 is null or lower(c.contact) like lower(concat('%', ?1, '%'))) "
			+ "or (?1 is null or lower(c.description) like lower(concat('%', ?1, '%'))) "
			+ "or (?1 is null or lower(c.phone) like lower(concat('%', ?1, '%'))) "
			+ "or (?1 is null or lower(c.website) like lower(concat('%', ?1, '%'))) "
			+ ")", countQuery = "select count(*) from company c " + "where c.is_active = true and ("
					+ "(?1 is null or lower(c.address) like lower(concat('%', ?1, '%'))) "
					+ "or (?1 is null or lower(c.company_name) like lower(concat('%', ?1, '%'))) "
					+ "or (?1 is null or lower(c.contact) like lower(concat('%', ?1, '%'))) "
					+ "or (?1 is null or lower(c.description) like lower(concat('%', ?1, '%'))) "
					+ "or (?1 is null or lower(c.phone) like lower(concat('%', ?1, '%'))) "
					+ "or (?1 is null or lower(c.website) like lower(concat('%', ?1, '%'))) " + ")", nativeQuery = true)
	public Page<CompanyDto> queryCompany(String query, Pageable pageable);

	@Modifying
	@Transactional
	@Query("UPDATE Company c SET c.isActive = ?2 WHERE c.id = ?1")
	public int updateStatus(Integer recruitmentId, boolean active);

	@Query("SELECT c FROM Company c where c.user.id = ?1")
	public Company getCompanyByUserId(Integer userId);

}