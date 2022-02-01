package com.ps.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ps.model.Role;
import com.ps.model.User;
import com.ps.repo.RoleReposirtoy;
import com.ps.repo.UserRepository;
import com.ps.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private RoleReposirtoy roleRepo;

	@Override
	public Integer saveUser(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		
		return repo.save(user).getId();
	}
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public List<User> getAllUsers() {
		return (List<User>) userRepo.findAll();
	}
	

	
	public List<Role> listRoles() {
		return  roleRepo.findAll();
	}
	
	
	
	@Override
	public User getOneUser(Integer id) {
		User user = null;
		Optional<User> opt = userRepo.findById(id);
		if (opt.isPresent()) {
			user = opt.get();
		}
		return user;
	}

	@Override
	public void deleteById(Integer id) {
		userRepo.deleteById(id);
	}

	@Override
	public boolean isUserExist(Integer id) {
		boolean status = userRepo.existsById(id);
		return status;
	}

	@Override
	public Integer updateUser(User user) {
		return userRepo.save(user).getId();
	}
	
	public User findByFirstName(String firstName) {
		return userRepo.findByFirstName(firstName);
	}



	@Override
	public User findByEmail(String email) {
	
    return userRepo.findByEmail(email);
				
	}
}
