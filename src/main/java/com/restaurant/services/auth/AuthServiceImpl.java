package com.restaurant.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.restaurant.dtos.SignupRequest;
import com.restaurant.dtos.UserDto;
import com.restaurant.entities.User;
import com.restaurant.enums.UserRole;
import com.restaurant.repositories.UserRepository;

import jakarta.annotation.PostConstruct;


@Service
public class AuthServiceImpl implements AuthService{
	
//	@Autowired
//	private UserRepository userRepository;
//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private final UserRepository userRepository;

	public AuthServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@PostConstruct
	public void createAdminAccount() {
		User adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
		
		if(adminAccount == null) {
			User user = new User();
			user.setName("Admin");
			user.setEmail("admin@test.com");
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			user.setUserRole(UserRole.ADMIN);
			userRepository.save(user);
		}
	}

	@Override
	public UserDto createUser(SignupRequest signupRequest) {
		User user = new User();
		user.setName(signupRequest.getName());
		user.setEmail(signupRequest.getEmail());
		user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
		user.setUserRole(UserRole.CUSTOMER);
		User userCreated = userRepository.save(user);
		
		UserDto createdUserDto = new UserDto();
		createdUserDto.setEmail(userCreated.getEmail());
		createdUserDto.setName(userCreated.getName());
		createdUserDto.setId(userCreated.getId());
		createdUserDto.setUserRole(userCreated.getUserRole());
		return createdUserDto;
	}

	
}
