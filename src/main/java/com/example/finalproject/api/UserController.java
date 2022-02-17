package com.example.finalproject.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.entity.User;
import com.example.finalproject.payload.request.UpdateUserRequest;
import com.example.finalproject.payload.response.MessageResponse;
import com.example.finalproject.repository.UserRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/user")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	PasswordEncoder encoder;

	@GetMapping(path = "/getAll")
	public ResponseEntity getAllRecruitments(@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "size", required = true) int size) {
		// This returns a JSON or XML with the users
//		Page<Recruitment> findAll = recruitmentRepository.findAll(PageRequest.of(page - 1, size));
		Page<User> findAll = userRepository.findAll(PageRequest.of(page - 1, size));
		return ResponseEntity.ok(findAll);
	}

	@DeleteMapping(path = "/delete/{id}")
	public ResponseEntity deleteUser(@PathVariable("id") Integer id) {
		if (id != 1) {
			userRepository.deleteById(id);
			return ResponseEntity.ok("Xóa người dùng thành công!");
		}
		return ResponseEntity.badRequest().body(new MessageResponse("Không thể xóa tài khoản admin!"));
	}

	@GetMapping("/{id}")
	public User getUserById(@PathVariable(name = "id") Integer id) {
		User user = userRepository.findById(id).get();
		return user;
	}

	@PutMapping("/updateInfo")
	@ResponseBody
	public ResponseEntity updateInfoUser(@RequestBody UpdateUserRequest payload) {
		System.out.println(payload);
		return ResponseEntity.ok(null);
	}

}
