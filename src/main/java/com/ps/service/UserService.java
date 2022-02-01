package com.ps.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ps.model.Product;
import com.ps.model.Role;
import com.ps.model.User;

public interface UserService {
	
	public Integer saveUser(User user);
	
	public List<User> getAllUsers();
	
	public User getOneUser(Integer id);
	
	public void deleteById(Integer id);
	
	boolean  isUserExist(Integer id); 
	
	public Integer updateUser(User user);
	
	public User findByFirstName(String firstName);
	
	public User findByEmail(String email);
	
	public List<Role> listRoles();
	
}
