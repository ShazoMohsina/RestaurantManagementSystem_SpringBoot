package com.restaurant.services.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.restaurant.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor //not working
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}


	@Override
	public UserDetailsService userDetailsService() {
		
		return new UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				return userRepository.findFirstByEmail(username)
									 .orElseThrow(() -> new UsernameNotFoundException("user not Found"));
				
			}
		};
	}

}
