package com.example.finalproject.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Recruitment extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "company_id")
//	@JsonBackReference
	@JsonIgnoreProperties("recruitments")
	private Company company;

	private String jobTitle;

	private Integer amountEmployee;

	private Date dateRecruitment;

	private String jobDescription;

	private String jobRequirements;

	private String jobBenefits;

	private String salary;

	private String workExperience;

	@ManyToOne
	@JoinColumn(name = "career_id")
	@JsonBackReference
	private Career career;

	@ManyToOne
	@JoinColumn(name = "level_id")
//	@JsonBackReference
	@JsonIgnoreProperties("recruitments")
	private Level level;

	@ManyToOne
	@JoinColumn(name = "type_work_id")
//	@JsonBackReference
	@JsonIgnoreProperties("recruitments")
	private TypeWork typeWork;

	private String address;

	@ManyToMany(mappedBy = "recruiments")
	@JsonManagedReference
	private List<Resume> resumes;

	private String status;

	private String ads_status;

	private boolean isActive;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
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

	public Career getCareer() {
		return career;
	}

	public void setCareer(Career career) {
		this.career = career;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public TypeWork getTypeWork() {
		return typeWork;
	}

	public void setTypeWork(TypeWork typeWork) {
		this.typeWork = typeWork;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Resume> getResumes() {
		return resumes;
	}

	public void setResumes(List<Resume> resumes) {
		this.resumes = resumes;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAds_status() {
		return ads_status;
	}

	public void setAds_status(String ads_status) {
		this.ads_status = ads_status;
	}

	public String getWorkExperience() {
		return workExperience;
	}

	public void setWorkExperience(String workExperience) {
		this.workExperience = workExperience;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "Recruitment [id=" + id + ", company=" + company + ", jobTitle=" + jobTitle + ", amountEmployee="
				+ amountEmployee + ", dateRecruitment=" + dateRecruitment + ", jobDescription=" + jobDescription
				+ ", jobRequirements=" + jobRequirements + ", jobBenefits=" + jobBenefits + ", salary=" + salary
				+ ", workExperience=" + workExperience + ", career=" + career + ", level=" + level + ", typeWork="
				+ typeWork + ", address=" + address + ", resumes=" + resumes + ", status=" + status + ", ads_status="
				+ ads_status + "]";
	}

}
