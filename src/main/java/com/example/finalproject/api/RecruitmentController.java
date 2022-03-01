package com.example.finalproject.api;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.dto.RecruitmentDto;
import com.example.finalproject.entity.Candidate;
import com.example.finalproject.entity.Recruitment;
import com.example.finalproject.entity.Resume;
import com.example.finalproject.entity.User;
import com.example.finalproject.payload.request.AddResumeDto;
import com.example.finalproject.payload.response.MessageResponse;
import com.example.finalproject.repository.CandidateRepository;
import com.example.finalproject.repository.RecruitmentRepository;
import com.example.finalproject.repository.ResumeRepository;
import com.example.finalproject.repository.UserRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/recruitment")
public class RecruitmentController {
	@Autowired
	private RecruitmentRepository recruitmentRepository;
	@Autowired
	private ResumeRepository resumeRepository;
	@Autowired
	private CandidateRepository candidateRepository;
	@Autowired
	private UserRepository userRepository;

	@GetMapping(path = "/getAll")
	public ResponseEntity getAllRecruitments(@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "size", required = true) int size) {
		Page<Recruitment> findAll = recruitmentRepository
				.findAllItem(PageRequest.of(page - 1, size, Sort.by("ads_status").descending()));
		return ResponseEntity.ok(findAll);
	}

	@GetMapping("/{id}")
	public Recruitment getRecruitmentById(@PathVariable(name = "id") Integer id) {
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
		Set<Resume> resumes = recruitment.getResumes();
		Optional<Resume> findById = resumeRepository.findById(dto.getResumeId());
		if (resumes != null && resumes.size() == 0) {
			Resume resume = findById.get();
			if (resume != null) {
				resumes.add(resume);
				recruitment.setResumes(resumes);
				recruitmentRepository.save(recruitment);
				Set<Recruitment> recruiments = resume.getRecruitments();
				recruiments.add(recruitment);
				resumeRepository.save(resume);

				return ResponseEntity.ok("Thành công");
			} else {
				return ResponseEntity.badRequest()
						.body(new MessageResponse("Không tìm thấy thông tin CV! Vui lòng liên hệ với admin"));
			}
		}
		// Check CV đã được ứng tuyển
		boolean anyMatch = resumes.stream().anyMatch(o -> o.getId() == dto.getResumeId());
		if (anyMatch) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("CV này đã ứng tuyển cho công việc này. Vui lòng không ứng tuyển thêm!"));
		}
		resumes.add(findById.get());
		recruitment.setResumes(resumes);
		return ResponseEntity.ok(recruitmentRepository.save(recruitment));

	}

	@GetMapping("/checkApply/{id}")
	public ResponseEntity checkCandidateIsApplied(@PathVariable(name = "id") Integer id, HttpServletRequest request) {
		Recruitment recruitment = recruitmentRepository.findById(id).get();
		if (id == null || recruitment == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy thông tin công việc!"));
		}
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		User user = userRepository.findByUsername(username).get();
		Candidate findByUserId = candidateRepository.findByUserId(user.getId());
		Set<Resume> resumes = recruitment.getResumes();
		if (resumes.size() == 0) {
			return ResponseEntity.ok(false);
		}
		boolean anyMatch = resumes.stream().anyMatch(o -> o.getCandidate().getId() == findByUserId.getId());
		if (anyMatch) {
			return ResponseEntity.ok(true);
		}
		return ResponseEntity.ok(false);
	}

	@GetMapping("/applied")
	public ResponseEntity getRecruitmentApplied(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		User user = userRepository.findByUsername(username).get();
		Candidate findByUserId = candidateRepository.findByUserId(user.getId());
		Page<RecruitmentDto> recruitmentApplied = recruitmentRepository.getRecruitmentApplied(findByUserId.getId(),
				PageRequest.of(page - 1, size));
		return ResponseEntity.ok(recruitmentApplied);
	}

	@PutMapping("/cancelResume/{recruitmentId}")
	@Modifying
	@Transactional
	public ResponseEntity cancelResume(@PathVariable("recruitmentId") Integer recruitmentId) {
//		if (recruitmentId == null) {
//			return ResponseEntity.badRequest()
//					.body(new MessageResponse("Không tìm thấy thông tin! Vui lòng liên hệ với admin"));
//		}
//		Recruitment recruitment = recruitmentRepository.findById(recruitmentId).get();
//		if (recruitment == null) {
//			return ResponseEntity.badRequest()
//					.body(new MessageResponse("Không tìm thấy thông tin! Vui lòng liên hệ với admin"));
//		}
//		Set<Resume> resumes = recruitment.getResumes();
//		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		String username = userDetails.getUsername();
//		User user = userRepository.findByUsername(username).get();
//		Resume resume = resumes.stream().filter(p -> p.getCandidate().getUser().getId() == user.getId()).findFirst()
//				.get();
//		if (resume != null) {
//			resumes.remove(resume);
//		}
//		recruitment.setResumes(resumes);
//		recruitmentRepository.save(recruitment);
//		Set<Recruitment> recruitments = resume.getRecruitments();
//		Recruitment recruitment2 = recruitments.stream().filter(item -> item.getId() == recruitment.getId()).findFirst().get();
//		recruitments.remove(recruitment2);
//		resume.setRecruitments(recruitments);
//		resumeRepository.save(resume);
		return ResponseEntity.ok("Hủy ứng tuyển thành công!");
	}

	@GetMapping("/reference")
	public ResponseEntity getRecruitmentByEmployerId(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		User user = userRepository.findByUsername(username).get();
		Page<RecruitmentDto> recruitmentsByEmployerId = recruitmentRepository.getRecruitmentsByEmployerId(user.getId(), PageRequest.of(page - 1, size));
		return ResponseEntity.ok(recruitmentsByEmployerId);
	}
	
	@DeleteMapping(path = "/delete/{id}")
	public ResponseEntity deleteRecruitment(@PathVariable("id") Integer id) {
		recruitmentRepository.deleteById(id);
		return ResponseEntity.ok("Xóa thông tin tuyển dụng thành công!");

	}

}
