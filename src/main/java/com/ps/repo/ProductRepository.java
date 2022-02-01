package com.ps.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.ps.model.Product;
import com.ps.model.User;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer>{
	
	
	   @Modifying
	   @Query("UPDATE Product SET isAvailable=:status WHERE id=:id")
	   public int updateProductActivate(boolean status, Integer id);

}
