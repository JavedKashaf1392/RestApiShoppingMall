package com.ps.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ps.model.Product;

public interface DeleteRepository extends JpaRepository<Product, Integer>{
	
	public List<Product> findByIdIn(Integer[] id);


}
