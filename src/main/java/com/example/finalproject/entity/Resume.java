package com.example.finalproject.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Resume extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String title;

	private String avatar;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "resume_recruitment", joinColumns = @JoinColumn(name = "recruitment_id"), inverseJoinColumns = @JoinColumn(name = "resume_id"))
	private List<Recruitment> recruiments;

    @ManyToOne
    @JoinColumn(name = "candidate_id") // thông qua khóa ngoại address_id
    private Candidate candidate;

	private String content;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public List<Recruitment> getRecruiments() {
		return recruiments;
	}

	public void setRecruiments(List<Recruitment> recruiments) {
		this.recruiments = recruiments;
	}

	

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Resume [id=" + id + ", title=" + title + ", avatar=" + avatar + ", recruiments=" + recruiments
				+ ", candidate=" + candidate + ", content=" + content + "]";
	}

	

}
