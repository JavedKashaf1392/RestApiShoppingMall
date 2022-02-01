package com.ps.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ps.model.Product;
import com.ps.model.User;

public interface ProductService {
	
	public Integer SaveProduct(Product product);
	

	public List<Product> getAllProducts();
	
	public Product getOneProduct(Integer id);
	
	public void deleteById(Integer id);
	
	boolean  isProductExist(Integer id); 
	
	public Integer updateProduct(Product product);
	
	
	//activate of the produt
	int updateProductStatus(boolean status, Integer id);
	
	
	//for delete multiple records by checks
	public List<Product> getByMultipleIds(Integer[] ids);
	

}
