package com.example.finalproject.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.dto.RecruitmentDto;
import com.example.finalproject.entity.Recruitment;
import com.example.finalproject.entity.Resume;
import com.example.finalproject.payload.request.AddResumeDto;
import com.example.finalproject.payload.response.MessageResponse;
import com.example.finalproject.repository.RecruitmentRepository;
import com.example.finalproject.repository.ResumeRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/recruitment")
public class RecruitmentController {
	@Autowired
	private RecruitmentRepository recruitmentRepository;
	@Autowired
	private ResumeRepository resumeRepository;

	@GetMapping(path = "/getAll")
	public ResponseEntity getAllRecruitments(@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "size", required = true) int size) {
		// This returns a JSON or XML with the users
//		Page<Recruitment> findAll = recruitmentRepository.findAll(PageRequest.of(page - 1, size));
		Page<Recruitment> findAll = recruitmentRepository
				.findAllItem(PageRequest.of(page - 1, size, Sort.by("ads_status").descending()));
		return ResponseEntity.ok(findAll);
	}

	@GetMapping("/{id}")
	public Recruitment getRecruitmentById(@PathVariable(name = "id") Integer id) {
//		recruitmentRepository.
		Optional<Recruitment> findById = recruitmentRepository.findById(id);
		Recruitment recruitment = findById.get();
		return recruitment;
	}

	@GetMapping("/reference/company/{companyId}/recruitment/{recruitmentId}")
	public ResponseEntity getRecruitmentReferenceByCompanyId(@PathVariable(name = "companyId") Integer companyId,
			@PathVariable(name = "recruitmentId") Integer recruitmentId) {
		List<RecruitmentDto> recruitmentReferenceByCompanyId = recruitmentRepository
				.getRecruitmentReferenceByCompanyId(companyId, recruitmentId);
		return ResponseEntity.ok(recruitmentReferenceByCompanyId);
	}

	@GetMapping("/reference/recruitment/{recruitmentId}")
	public ResponseEntity getRecruitmentsReference(@PathVariable(name = "recruitmentId") Integer recruitmentId) {
		if (recruitmentId == null || recruitmentId <= 0) {
			return null;
		}
		Recruitment recruitment = recruitmentRepository.findById(recruitmentId).get();
		if (recruitment == null) {
			return null;
		}
		List<RecruitmentDto> recruitmentsReference = recruitmentRepository.getRecruitmentsReference(recruitment);
		return ResponseEntity.ok(recruitmentsReference);
	}

	@PutMapping("/status")
	public ResponseEntity updateStatusRecruitment(@RequestParam(value = "status", required = true) boolean status,
			@RequestParam(value = "id", required = true) int id) {
		recruitmentRepository.updateStatus(id, status);
		return ResponseEntity.ok("Update trạng thái thành công");

	}

	@PostMapping("/addResume")
	@Modifying
	@Transactional
	public ResponseEntity addResume(@RequestBody AddResumeDto dto) {
		Recruitment recruitment = recruitmentRepository.findById(dto.getRecruitmentId()).get();
		List<Resume> resumes = recruitment.getResumes();
		if (resumes != null && resumes.size() == 0) {
			Resume resume = resumeRepository.findById(dto.getResumeId()).get();
			if (resume != null) {
				resumes.add(resume);
				recruitment.setResumes(resumes);
				recruitmentRepository.save(recruitment);
				List<Recruitment> recruiments = resume.getRecruiments();
				recruiments.add(recruitment);
				resumeRepository.save(resume);
				return ResponseEntity.ok("Thành công");
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy thông tin CV!"));
			}
		}
		// Check CV đã được ứng tuyển
		boolean anyMatch = resumes.stream().anyMatch(o -> o.getId() == dto.getResumeId());
		if (anyMatch) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("CV này đã ứng tuyển cho công việc này. Vui lòng không ứng tuyển thêm!"));
		}
		resumes.add(resumeRepository.findById(dto.getResumeId()).get());
		recruitment.setResumes(resumes);
		return ResponseEntity.ok(recruitmentRepository.save(recruitment));

	}

}
