package com.example.finalproject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

	@Query(value = "Select  r.id, r.job_title as jobTitle, datediff(r.date_recruitment, now()) as dateDiff, l.name_level as nameLevel, tw.name_type_work as nameTypeWork, c.avatar as avatarCom, c.id as companyId "
			+ "FROM Recruitment r " + "join level l " + "on r.level_id = l.id " + "join type_work tw "
			+ "on r.type_work_id = tw.id " + "join company c on r.company_id = c.id "
			+ "where r.career_id = :#{#recruitment.career.id} and r.id != :#{#recruitment.id} and datediff(r.date_recruitment, now()) > 0 limit 5", nativeQuery = true)
	public List<RecruitmentDto> getRecruitmentsReference(@Param("recruitment") Recruitment recruitment);
}
