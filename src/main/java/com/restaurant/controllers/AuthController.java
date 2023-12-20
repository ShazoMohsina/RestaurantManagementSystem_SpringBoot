package com.restaurant.controllers;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dtos.AuthenticationRequest;
import com.restaurant.dtos.AuthenticationResponse;
import com.restaurant.dtos.SignupRequest;
import com.restaurant.dtos.UserDto;
import com.restaurant.entities.User;
import com.restaurant.repositories.UserRepository;
import com.restaurant.services.auth.AuthService;
import com.restaurant.services.jwt.UserDetailsServiceImpl;
import com.restaurant.util.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
//	@Autowired
//	private AuthService authService;

	private final AuthService authService;
	private final AuthenticationManager authenticationManager;
	private final UserDetailsServiceImpl userDetailsServiceImpl;
	private final JwtUtil jwtUtil;
	private final UserRepository userRepository;
	
	public AuthController(AuthService authService, AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsServiceImpl, JwtUtil jwtUtil, UserRepository userRepository) {
		this.authService = authService;
		this.authenticationManager = authenticationManager;
		this.userDetailsServiceImpl = userDetailsServiceImpl;
		this.jwtUtil = jwtUtil;
		this.userRepository = userRepository;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
		UserDto createdUserDto = authService.createUser(signupRequest);
		
		if(createdUserDto == null) {
			return new ResponseEntity<>("User not created. Try again later. ", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
															HttpServletResponse response) throws IOException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
																					   authenticationRequest.getPassword()));
		}catch (BadCredentialsException be) {
			throw new BadCredentialsException("Incorrect Username or Password");
		}catch (DisabledException de) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not Active");
			return null;
		}
		
		final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(authenticationRequest.getEmail());
		final String jwt = jwtUtil.generateToken(userDetails);
		Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
		
		if(optionalUser.isPresent()) {
			authenticationResponse.setJwt(jwt);
			authenticationResponse.setUserRole(optionalUser.get().getUserRole());
			authenticationResponse.setUserId(optionalUser.get().getId());
		}else {
			return null;
		}
		
		return authenticationResponse;
		
	}
}

