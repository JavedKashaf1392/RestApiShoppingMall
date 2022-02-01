package com.ps.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ps.config.CategoryPageInfo;
import com.ps.exception.CategoryNotFoundException;
import com.ps.exception.UserNotFoundException;
import com.ps.model.Category;
import com.ps.model.Product;
import com.ps.model.ResponseMessage;
import com.ps.model.User;
import com.ps.service.CategoryJson;
import com.ps.service.CategoryService;
import com.ps.service.FilesStorageService;
import com.ps.service.UserJson;
import com.ps.serviceImpl.DeleteService;
import com.ps.view.ProductPdfView;


@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {
	
	@Autowired
	private CategoryService service;
	
	 @Autowired
	  FilesStorageService storageService;
	
	@Autowired
	private DeleteService deleteService;
	
	//1.Fetch All Data is here
	@GetMapping("/categories")
	public ResponseEntity<?> fetchAllCategory(
			){
		ResponseEntity<?> resp=null;
		try {
		      Map<String,Object> response = new HashMap<>();
		      return new ResponseEntity<>(response,HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	
	
	
	//2.Fetch All Data for forms showing the Categories
	@GetMapping("/categoriesForm")
	public ResponseEntity<?> newCategory() {
		ResponseEntity<?> resp=null;
		List<Category> listCategories = service.CategoreiesInForm();
	  return resp=new ResponseEntity<List<Category>>(listCategories,HttpStatus.OK);
	}
	
	
	
	//3.Save Category
//		@PostMapping("/categories/save")
//		public ResponseEntity<?> createCategory(@RequestBody Category category) throws IOException {
//			ResponseEntity<?> resp=null;
//			try {	
//				if(category!=null) {
//					System.out.println("category data"+category);
//					Integer saveCategory=service.saveCategory(category);
//					resp=new ResponseEntity<>("Category Saved Successfully: "+saveCategory,HttpStatus.OK);
//				}
//			} catch (Exception e) {
//			   resp=new ResponseEntity<String>("Category not Addedd",HttpStatus.INTERNAL_SERVER_ERROR);
//			}
//			return resp;
//			}
		
		
		
		 @PostMapping(value = "/categories/save")
				  public ResponseEntity<ResponseMessage> uploadFile1(@RequestParam("file") MultipartFile multipartFile,
				  @RequestPart("category") String category)  {
			               String message="";
			               Integer id = 0;
			               
					  Category categorys = CategoryJson.getJson(category);
					  System.out.println("category"+categorys);
					  String fileName=StringUtils.cleanPath(multipartFile.getOriginalFilename());
					  try {
					  if(!multipartFile.isEmpty()) {
						  System.out.println("multipart data is here"+multipartFile);
//						  Resource file = storageService.load(fileName);
//						  storageService.save(fileName);
						  String uploadDir="user-photo";
						  categorys.setImage(fileName);
						  Integer saveCategory=service.saveCategory(categorys);
					      storageService.save(uploadDir, fileName, multipartFile);
					  }else {
						  
					  }
				      message = "Category Saved Succcessfully:" + id;
				      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
				    } catch (Exception e) {
				      message = "Could not upload the file: "+multipartFile.getOriginalFilename() + "!";
				      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
				    }
				  }
		
		
		
		
		
		
		
		
		
		
		
		
		
		
          
//3.get one user
	@GetMapping("/{id}")
	public ResponseEntity<?> getCategoryDetail(@PathVariable(required = true) Integer id) {
		System.out.println();
		ResponseEntity<String> resp = null;

		try {
			Category  category= service.getCategoryId(id);
			if (category!=null) {
				return new ResponseEntity<Category>(category, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("No Category exist with id:"+id,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		} catch (Exception e) {
			throw  new UserNotFoundException("please try after some time");	
		}
	}
	
	
	//5.update one user
	@PutMapping("/update")
	public ResponseEntity<String> updateCategory(@RequestBody Category category){
		ResponseEntity<String> resp = null;
		//find the user exist or not 
		Integer categoryId=category.getId();
		try {
			if(service.isCategoryExist(categoryId)) {
				Integer id = service.updateCategory(category);
				
				resp=new ResponseEntity<String>("Category Updated Successfully with id: "+id,HttpStatus.OK);
			}
			else {
				resp=new ResponseEntity<String>("Category not exist",HttpStatus.BAD_REQUEST);
			}	
		} catch (Exception e) {
			throw new UserNotFoundException("Pleace contact with Amin and Try later");
		}
		return resp;
	}

	




//4.delete one user
	@DeleteMapping("/remove/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Integer id){
		ResponseEntity<String> resp=null;
		try {
				service.deleteById(id);
				resp=new ResponseEntity<String>("Record Deleted"+id,HttpStatus.OK);
		} catch (Exception e) {
			throw new UserNotFoundException("please try after sometime");
		}
		return resp;
	}


          //10. Export One row to PDF File
          @GetMapping("pdf/{id}")
public ModelAndView exportOnePdf(@PathVariable Integer[] id) {
	ModelAndView m = new ModelAndView();
	m.setView(new ProductPdfView());
	m.addObject("list",service.getByMultipleIds(id));
	return m;
}
}







	/*@GetMapping("/categories/{id}/enabled/{status}")
	public String updateCategoryEnabledStatus(@PathVariable("id") Integer id,
			@PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
		service.updateCategoryEnabledStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The category ID " + id + " has been " + status;
		redirectAttributes.addFlashAttribute("message", message);
		
		return "redirect:/categories";
	}*/
	
	
	
	
	
	/*@GetMapping("/categories/delete/{id}")
	public String deleteCategory(@PathVariable(name = "id") Integer id, 
			Model model,
			RedirectAttributes redirectAttributes) {
		try {
			service.delete(id);
			String categoryDir = "../category-images/" + id;
			FileUploadUtil.removeDir(categoryDir);
			
			redirectAttributes.addFlashAttribute("message", 
					"The category ID " + id + " has been deleted successfully");
		} catch (CategoryNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}
		
		return "redirect:/categories";
	}*/
	