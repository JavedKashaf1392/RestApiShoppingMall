package com.ps.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ps.model.Category;
import com.ps.model.Product;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer>{
	
	@Query("SELECT c from Category c WHERE c.parent.id is NULL")
	public List<Category> findRootCategory(Sort sort);
	
	
	@Query("SELECT c from Category c WHERE c.parent.id is NULL")
	public List<Category> findRootCategory();
	
	@Query("SELECT c from Category c WHERE c.name LIKE %?1%")
	public Page<Category> search(String keyword,Pageable pageable,Integer size);
	
	@Query("SELECT c from Category c WHERE c.parent.id is NULL")
    public Page<Category> findRootCategory(Pageable pageNum,Integer size);
	
	public Long countById(Integer id);
	
	public Category findByName(String name);
	
	public Category findByAlias(String alias);
	
	@Query("UPDATE Category c SET c.enabled = ?2 WHERE c.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);
	
	@Query("select c from Category c where c.id = ?1")
	public Category getCategoryId(Integer id);
	
	
	
	
	
}
