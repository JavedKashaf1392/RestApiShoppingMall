package com.ps.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ps.model.Category;
import com.ps.model.Product;
import com.ps.repo.CategoryRepository;
import com.ps.repo.DeleteCategoryRepo;
import com.ps.repo.DeleteRepository;
import com.ps.service.CategoryServiceI;

@Service
public class DeleteService {
	
	
	@Autowired
	private DeleteRepository repo;
	
	@Autowired
	private DeleteCategoryRepo categoryRepo;
	
	
	public void deleteWelcomeByIds(Integer[] ids) {
	    List<Product> list = repo.findByIdIn(ids);
	    repo.deleteInBatch(list);
	}
	
	public void DeleteCategoryByIds(Integer[] ids) {
	    List<Category> list = categoryRepo.findByIdIn(ids);
	    categoryRepo.deleteInBatch(list);
	}

}
