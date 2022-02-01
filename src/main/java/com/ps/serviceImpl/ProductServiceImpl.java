package com.ps.serviceImpl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ps.model.Product;
import com.ps.model.User;
import com.ps.repo.DeleteRepository;
import com.ps.repo.ProductRepository;
import com.ps.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository prodRepo;
	
	
	@Autowired
	private DeleteRepository deleteRepo;

	@Override
	public Integer SaveProduct(Product product) {
		return prodRepo.save(product).getId();
	}

	@Override
	public Product getOneProduct(Integer id) {
		Product product=null;
		Optional<Product> opt=prodRepo.findById(id);
		if(opt.isPresent()) {
			product=opt.get();
		}
		return product;
	}

	@Override
	public void deleteById(Integer id) {
		prodRepo.deleteById(id);
		
	}

	@Override
	public boolean isProductExist(Integer id) {
		prodRepo.existsById(id);
		return true;
	}

	@Override
	public Integer updateProduct(Product product) {
		return prodRepo.save(product).getId();
	}

	@Override
	public List<Product> getAllProducts() {
		return (List<Product>) prodRepo.findAll();
	}
	
	

	

	
	@Override
	@Transactional
	public int updateProductStatus(boolean status, Integer id) {
		return prodRepo.updateProductActivate(status, id);
	}
	
	
	@Override
	public List<Product> getByMultipleIds(Integer[] ids) {
		List<Product> list=deleteRepo.findByIdIn(ids);
		return list;
	}
	
//	@Override
//	public boolean deleteWelcomeByIds(Integer[] ids) {
//		Page<Product> list=prodRepo.findByIdIn(ids);
//		prodRepo.deleteInBatch(list);
//		return true;
//	}

	

}
