package com.ps.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ps.model.Category;

public interface DeleteCategoryRepo extends JpaRepository<Category, Integer>{
	
	public List<Category> findByIdIn(Integer[] id);

}
