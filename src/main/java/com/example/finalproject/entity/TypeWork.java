package com.example.finalproject.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class TypeWork extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToMany(mappedBy = "typeWork", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Recruitment> recruitments;

	private String nameTypeWork;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Recruitment> getRecruitments() {
		return recruitments;
	}

	public void setRecruitments(List<Recruitment> recruitments) {
		this.recruitments = recruitments;
	}

	public String getNameTypeWork() {
		return nameTypeWork;
	}

	public void setNameTypeWork(String nameTypeWork) {
		this.nameTypeWork = nameTypeWork;
	}

	@Override
	public String toString() {
		return "TypeWork [id=" + id + ", recruitments=" + recruitments + ", nameTypeWork=" + nameTypeWork + "]";
	}

}
