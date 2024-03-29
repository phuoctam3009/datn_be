package com.example.finalproject.api;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.entity.Candidate;
import com.example.finalproject.entity.Company;
import com.example.finalproject.entity.ERole;
import com.example.finalproject.entity.Role;
import com.example.finalproject.entity.User;
import com.example.finalproject.payload.request.LoginRequest;
import com.example.finalproject.payload.request.SignupRequest;
import com.example.finalproject.payload.response.JwtResponse;
import com.example.finalproject.payload.response.MessageResponse;
import com.example.finalproject.repository.RoleRepository;
import com.example.finalproject.repository.UserRepository;
import com.example.finalproject.repository.CandidateRepository;
import com.example.finalproject.repository.CompanyRepository;

import com.example.finalproject.security.jwt.JwtUtils;
import com.example.finalproject.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class LoginController {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CandidateRepository candidateRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Tên đăng nhập đã tồn tại!"));
		}
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Email đã tồn tại!"));
		}
		// Create new user's account
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		if (strRoles == null) {
			Role userRole = roleRepository.findByRoleName(ERole.ROLE_CANDIDATE)
					.orElseThrow(() -> new RuntimeException("Không tìm thấy role"));
			roles.add(userRole);
		} else {
			for (String role : strRoles) {
				switch (role) {
				case "ROLE_ADMIN":
					Role adminRole = roleRepository.findByRoleName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Không tìm thấy role"));
					roles.add(adminRole);
				case "ROLE_EMPLOYEE":
					Role modRole = roleRepository.findByRoleName(ERole.ROLE_EMPLOYEE)
							.orElseThrow(() -> new RuntimeException("Không tìm thấy role"));
					roles.add(modRole);
				default:
					Role userRole = roleRepository.findByRoleName(ERole.ROLE_CANDIDATE)
							.orElseThrow(() -> new RuntimeException("Không tìm thấy role"));
					roles.add(userRole);
				}
			}

//			strRoles.forEach(role -> {
//				switch (role) {
//				case "admin":
//					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//					roles.add(adminRole);
//					break;
//				case "employee":
//					Role modRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
//							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//					roles.add(modRole);
//					break;
//				default:
//					Role userRole = roleRepository.findByName(ERole.ROLE_CANDIDATE)
//							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//					roles.add(userRole);
//				}
//			});
		}
		user.setRoles(roles);

		User userSaved = userRepository.save(user);
		for (Role role : roles) {
			if (role.getRoleName().name().equalsIgnoreCase("ROLE_CANDIDATE")) {
				Candidate candidate = new Candidate();
				candidate.setUser(userSaved);
				candidateRepository.save(candidate);
			} else if (role.getRoleName().name().equalsIgnoreCase("ROLE_EMPLOYEE")) {
				Company company = new Company();
				company.setUser(userSaved);
				companyRepository.save(company);
			}
		}

		return ResponseEntity.ok(new MessageResponse("Đăng ký tài khoản thành công!"));
	}
}
