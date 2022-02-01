package com.ps.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ps.exception.UserNotFoundException;
import com.ps.model.Product;
import com.ps.repo.ProductRepository;
import com.ps.service.ProductService;
import com.ps.serviceImpl.DeleteService;
import com.ps.view.ExcelNewView;
import com.ps.view.ProductPdfView;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService ProdService;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private DeleteService deleteService;
	
	
	@GetMapping("/all")
	public ResponseEntity<Map<String,Object>> getAllData(){
		  
	    try {
	       List<Product> allProducts = ProdService.getAllProducts();
	      Map<String, Object> response = new HashMap<>();
	      response.put("list",allProducts);
	      return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	
	
    //2.save all usesrs
	@PostMapping("/save")
	public ResponseEntity<?> getProductDetails(@RequestBody(required = true) Product product) {
		ResponseEntity<?> resp = null;
	      
	try {
		if(product!=null) {
			Integer id=ProdService.SaveProduct(product);
			resp= new ResponseEntity("Product Saved Successfully",HttpStatus.CREATED);	
		}
	} catch (Exception e) {
	throw new UserNotFoundException("User Already save "+product.getProductName());
	}
	return resp;
	}
	

	//3.get one user
	@GetMapping("/{id}")
	public ResponseEntity<?> getProductDetails(@PathVariable(required = true) Integer id) {
		System.out.println();
		ResponseEntity<String> resp = null;

		try {
			Product  prodId= ProdService.getOneProduct(id);
			if (prodId!=null) {
				return new ResponseEntity<Product>(prodId, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("No Product exist with id:"+id,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		} catch (Exception e) {
			throw  new UserNotFoundException("please try after some time");	
		}
				
	}
	
	
	//4.delete one user
	@DeleteMapping("/remove/{ids}")
	public ResponseEntity<String> deleteProduct(@PathVariable Integer[] ids){
		
		ResponseEntity<String> resp=null;
		try {
				deleteService.deleteWelcomeByIds(ids);
				resp=new ResponseEntity<String>("Record Deleted"+ids,HttpStatus.OK);
		} catch (Exception e) {
			throw new UserNotFoundException("please try after sometime");
		}
		return resp;
	}
	
	
	

	//5.update one user
	@PutMapping("/update")
	public ResponseEntity<String> updateProduct(@RequestBody Product product){
		ResponseEntity<String> resp = null;
		
		//find the user exist or not 
		Integer userId=product.getId();
		
		try {
			if(ProdService.isProductExist(userId)) {
				ProdService.updateProduct(product);
				resp=new ResponseEntity<String>("User Record Update"+userId,HttpStatus.OK);
			}
			else {
				resp=new ResponseEntity<String>("User not exist",HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			throw new UserNotFoundException("Pleace contact with Amin and Try later");
		}
		
		return resp;
		
	}
	
	
	
	
	//10.for status update
			//activate
			@GetMapping("/activate/{id}")
			public ResponseEntity<String> activateUser(@PathVariable Integer id)
			{
				ResponseEntity<String> resp=null;
				ProdService.updateProductStatus(true, id);
				return resp=new ResponseEntity<String>("Activated Succefully",HttpStatus.OK);
			}
			
			//11.inactive
			@GetMapping("/inactive/{id}")
			public ResponseEntity<String> deActivateUser(@PathVariable Integer id)
			{
				ResponseEntity<String> resp=null;
				ProdService.updateProductStatus(false, id);
				return resp=new ResponseEntity<String>("Deactivated Succefully!!! ",HttpStatus.OK);
			}
				
			
				// 10. Export One row to PDF File
				@GetMapping("pdf/{id}")
				public ModelAndView exportOnePdf(@PathVariable Integer[] id) {
					ModelAndView m = new ModelAndView();
					m.setView(new ProductPdfView());
					m.addObject("list",ProdService.getByMultipleIds(id));
					return m;
				}
				
				 @GetMapping("/customers.xlsx/{id}")
				    public ResponseEntity excelCustomersReport(@PathVariable Integer[] id) throws IOException {
//				        List products = (List) ProdService.getAllProducts();
				        List<Product> products = ProdService.getByMultipleIds(id);
						
						ByteArrayInputStream in = ExcelNewView.customersToExcel(products);
						// return IOUtils.toByteArray(in);
						
						HttpHeaders headers = new HttpHeaders();
				        headers.add("Content-Disposition", "attachment; filename=customers.xlsx");
						
						 return ResponseEntity
					                .ok()
					                .headers(headers)
					                .body(new InputStreamResource(in));
				    }
}
