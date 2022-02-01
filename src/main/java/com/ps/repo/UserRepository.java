package com.ps.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.ps.model.Product;
import com.ps.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	public User findByFirstName(String firstName);

	@Query("SELECT u FROM User u WHERE u.email = :email")
	public User getUserByEmail(@Param("email") String email);
	
	public User findByEmail(String email);
		
	public List<User> findAll();
	
	

}
