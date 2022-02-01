package com.ps.serviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ps.model.User;
import com.ps.repo.UserRepository;


@Service
public class UserDetailsServiceSecurity implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetailsInfo loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.getUserByEmail(email);
		if (user != null) {
			return new UserDetailsInfo(user);
		}
		
		throw new UsernameNotFoundException("Could not find user with email: " + email);
	}

}
