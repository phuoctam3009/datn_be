package com.example.finalproject.payload.request;

import java.util.Date;

public class RecruitmentRequest {
	private Integer id;

	private String jobTitle;

	private Integer amountEmployee;
	private Date dateRecruitment;
	private String jobDescription;
	private String jobRequirements;
	private String jobBenefits;
	private String salary;
	private String workExperience;

	private Integer careerId;

	private Integer levelId;

	private Integer typeWorkId;
	private String address;

	private Integer userId;
	
	private Integer cityId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Integer getAmountEmployee() {
		return amountEmployee;
	}

	public void setAmountEmployee(Integer amountEmployee) {
		this.amountEmployee = amountEmployee;
	}

	public Date getDateRecruitment() {
		return dateRecruitment;
	}

	public void setDateRecruitment(Date dateRecruitment) {
		this.dateRecruitment = dateRecruitment;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public String getJobRequirements() {
		return jobRequirements;
	}

	public void setJobRequirements(String jobRequirements) {
		this.jobRequirements = jobRequirements;
	}

	public String getJobBenefits() {
		return jobBenefits;
	}

	public void setJobBenefits(String jobBenefits) {
		this.jobBenefits = jobBenefits;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getWorkExperience() {
		return workExperience;
	}

	public void setWorkExperience(String workExperience) {
		this.workExperience = workExperience;
	}

	public Integer getCareerId() {
		return careerId;
	}

	public void setCareerId(Integer careerId) {
		this.careerId = careerId;
	}

	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	public Integer getTypeWorkId() {
		return typeWorkId;
	}

	public void setTypeWorkId(Integer typeWorkId) {
		this.typeWorkId = typeWorkId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	
	

}
