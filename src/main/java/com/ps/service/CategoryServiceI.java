package com.ps.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ps.config.CategoryPageInfo;
import com.ps.model.Category;
import com.ps.model.Product;

public interface CategoryServiceI {
	
	public List<Category> listAll(CategoryPageInfo pageInfo,int pageNum,int size,String keyword);
	
	public List<Category> CategoreiesInForm();
	
	public Integer saveCategory(Category category);
	
	public Category createCategory(Category category);
	
	public String checkUnique(Integer id,String name,String alias);
	
	public Category getCategory(Integer id);
	
	boolean  isCategoryExist(Integer id); 
	
	public Integer updateCategory(Category category);
	
	public void deleteById(Integer id);
	
	public Category getCategoryId(Integer id);
	
	//for delete multiple records by checks
	public List<Category> getByMultipleIds(Integer[] ids);

	
}
