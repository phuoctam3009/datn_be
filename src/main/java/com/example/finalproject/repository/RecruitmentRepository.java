package com.example.finalproject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.finalproject.dto.RecruitmentDto;
import com.example.finalproject.entity.Recruitment;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Integer> {
	@Query("SELECT r FROM Recruitment r")
	public Page<Recruitment> findAllItem(Pageable pageable);

	@Query(value = "SELECT r.id, r.job_title as jobTitle, datediff(r.date_recruitment, now()) as dateDiff, l.name_level as nameLevel, tw.name_type_work as nameTypeWork "
			+ "FROM Recruitment r " + "join level l " + "on r.level_id = l.id " + "join type_work tw "
			+ "on r.type_work_id = tw.id "
			+ "where r.company_id = :companyId and r.id != :recruitmentId and datediff(r.date_recruitment, now()) > 0 limit 3", nativeQuery = true)
	public List<RecruitmentDto> getRecruitmentReferenceByCompanyId(@Param("companyId") Integer companyId,
			@Param("recruitmentId") Integer recruitmentId);
	
	@Query(value = "SELECT r.id, c.avatar as avatarCom, r.salary, r.amount_employee as amountEmployee, r.address, r.is_active as isActive, r.job_title as jobTitle, datediff(r.date_recruitment, now()) as dateDiff, l.name_level as nameLevel, tw.name_type_work as nameTypeWork "
			+ "FROM Recruitment r "
			+ "join level l "
			+ "on r.level_id = l.id "
			+ "join type_work tw "
			+ "on r.type_work_id = tw.id "
			+ "join Company c "
			+ "on r.company_id = c.id "
			+ "where c.user_id = ?1 and r.deleted = false", 
			countQuery = "select r.id from Recruitment r join Company c on r.company_id = c.id where c.user_id = ?1",
			nativeQuery = true)
	public Page<RecruitmentDto> getRecruitmentsByEmployerId(Integer userId, Pageable pageable);

	@Query(value = "Select  r.id, r.job_title as jobTitle, datediff(r.date_recruitment, now()) as dateDiff, l.name_level as nameLevel, tw.name_type_work as nameTypeWork, c.avatar as avatarCom, c.id as companyId "
			+ "FROM Recruitment r " + "join level l " + "on r.level_id = l.id " + "join type_work tw "
			+ "on r.type_work_id = tw.id " + "join company c on r.company_id = c.id "
			+ "where r.career_id = :#{#recruitment.career.id} and r.id != :#{#recruitment.id} and datediff(r.date_recruitment, now()) > 0 limit 5", nativeQuery = true)
	public List<RecruitmentDto> getRecruitmentsReference(@Param("recruitment") Recruitment recruitment);

	@Modifying
	@Transactional
	@Query("UPDATE Recruitment m SET m.isActive = ?2 WHERE m.id = ?1")
	public int updateStatus(Integer recruitmentId, boolean active);

	@Query(value = "Select r.id, r.job_title as jobTitle, r.salary as salary, r.address as address, ca.name_career as career, "
			+ "datediff(r.date_recruitment, now()) as dateDiff, c.avatar as avatarCom, c.id as companyId, c.company_name as companyName, r.id as resumeId "
			+ "FROM Recruitment r " + "join Career ca on r.career_id = ca.id "
			+ "join Company c on r.company_id = c.id " + "join resume_recruitment rr on r.id = rr.recruitment_id "
			+ "join resume re on rr.resume_id = re.id "
			+ "where re.candidate_id = ?1", 
			countQuery = "Select count(r.id) from recruitment r "
					+ "join Career ca on r.career_id = ca.id " + "join Company c on r.company_id = c.id "
					+ "join resume_recruitment rr on r.id = rr.recruitment_id "
					+ "join resume re on rr.resume_id = re.id " + "where re.candidate_id = ?1", nativeQuery = true)
	public Page<RecruitmentDto> getRecruitmentApplied(Integer candidateId, Pageable pageable);

}
