package com.example.finalproject.dto;

import com.example.finalproject.entity.Company;

public interface RecruitmentDto {
	String getJobTitle();
	
	Integer getDateDiff();

	String getNameLevel();
	
	String getCareer();
	
	String getSalary();
	
	String getAddress();

	String getNameTypeWork();
	
	String getAvatarCom();
	
	String getCompanyName();
	
	Integer getId();
	
	Integer getCompanyId();
	
	Company getCompany();
	
	Integer getResumeId();
	
	Integer getAmountEmployee();
	
	Boolean getIsActive();
	
	

}
