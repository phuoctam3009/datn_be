package com.example.finalproject.api;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.cloudinary.CloudinaryService;
import com.example.finalproject.entity.Candidate;
import com.example.finalproject.entity.Company;
import com.example.finalproject.payload.request.CompanyRequest;
import com.example.finalproject.payload.response.MessageResponse;
import com.example.finalproject.repository.CompanyRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/company")
public class CompanyController {
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private CloudinaryService cloudService;

	@GetMapping("/{id}")
	public Company getCompanyById(@PathVariable(name = "id") Integer id) {
		Company company = companyRepository.findById(id).get();
		return company;
	}

	@GetMapping("/getAll")
	public ResponseEntity getAllCompany(@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "size", required = true) int size) {
		Page<Company> findAllItem = companyRepository.findAllItem(PageRequest.of(page - 1, size));
		return ResponseEntity.ok(findAllItem);
	}
	
	@GetMapping("/getActive")
	public ResponseEntity getAllCompanyActive(@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "size", required = true) int size) {
		Page<Company> findAllItem = companyRepository.findAllItemActive(PageRequest.of(page - 1, size));
		return ResponseEntity.ok(findAllItem);
	}

	@PutMapping("/status")
	public ResponseEntity updateStatusCompany(@RequestParam(value = "status", required = true) boolean status,
			@RequestParam(value = "id", required = true) int id) {
		companyRepository.updateStatus(id, status);
		return ResponseEntity.ok("Update trạng thái thành công");

	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity getCompanyByUserId(@PathVariable(name="userId") Integer userId) {
		Company companyByUserId = companyRepository.getCompanyByUserId(userId);
		if(companyByUserId == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(companyByUserId);
	}
	
	@PutMapping("/updateProfile")
	public ResponseEntity updateProfileCandidate(@RequestBody CompanyRequest company) {
//		Candidate save = candidateRepository.save(candidate);
		Integer id = company.getId();
		if(id == null || id < 1) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Company companyParam = companyRepository.getById(id);
		if(companyParam == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		companyParam.setAddress(company.getAddress());
		companyParam.setCompanyName(company.getCompanyName());
		companyParam.setContact(company.getContact());
		companyParam.setDescription(company.getDescription());
		companyParam.setPhone(company.getPhone());
		companyParam.setWebsite(company.getWebsite());
		companyRepository.save(companyParam);
		return ResponseEntity.ok("Cập nhật thông tin công ty thành công");
	}
	
	@PutMapping("/updateAvatar")
	@ResponseBody
	public ResponseEntity updateAvatarCompany(@RequestParam("filePath") String filePath, @RequestParam("userId") Integer userId) {
		byte[] decodedBytes = DatatypeConverter
				.parseBase64Binary(filePath.replace("data:image/png;base64,", "").trim());
		File file = new File("D:/PhuocTam/file/test.png");
		try {
			FileUtils.writeByteArrayToFile(file, decodedBytes);
		} catch (IOException e) {
			System.out.println("Lỗi ghi file: " + e.toString());
			return ResponseEntity.badRequest().body(new MessageResponse("Có lỗi trong quá trình upload file!"));
		}
//        File file = new File("D:/PhuocTam/file/test.jpg");
//        Path write = Files.write(file.toPath(), decode);
		String uploadFile = cloudService.uploadFile(file);
		Company company = companyRepository.getCompanyByUserId(userId);
		company.setAvatar(uploadFile);
		companyRepository.save(company);
		return ResponseEntity.ok("Cập nhật avatar thành công!");
		
	}
	
	@PutMapping("/updateBg")
	@ResponseBody
	public ResponseEntity updateBackgroundCompany(@RequestParam("filePath") String filePath, @RequestParam("userId") Integer userId) {
		byte[] decodedBytes = DatatypeConverter
				.parseBase64Binary(filePath.replace("data:image/png;base64,", "").trim());
		File file = new File("D:/PhuocTam/file/test.png");
		try {
			FileUtils.writeByteArrayToFile(file, decodedBytes);
		} catch (IOException e) {
			System.out.println("Lỗi ghi file: " + e.toString());
			return ResponseEntity.badRequest().body(new MessageResponse("Có lỗi trong quá trình upload file!"));
		}
//        File file = new File("D:/PhuocTam/file/test.jpg");
//        Path write = Files.write(file.toPath(), decode);
		String uploadFile = cloudService.uploadFile(file);
		Company company = companyRepository.getCompanyByUserId(userId);
		company.setBackground(uploadFile);
		companyRepository.save(company);
		return ResponseEntity.ok("Cập nhật background thành công!");
		
	}
}
