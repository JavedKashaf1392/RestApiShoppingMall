package com.ps.controller;


import java.io.File;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ps.exception.UserNotFoundException;
import com.ps.model.HttpResponse;
import com.ps.model.Role;
import com.ps.model.User;
import com.ps.repo.UserRepository;
import com.ps.response.AuthenticationResponse;
import com.ps.response.UserRequest;
import com.ps.response.UserResponse;
import com.ps.response.UserResponse2;
import com.ps.service.FilesStorageService;
import com.ps.service.UserService;
import com.ps.serviceImpl.UserDetailsInfo;
import com.ps.util.JwtUtil;

import io.jsonwebtoken.impl.DefaultClaims;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	 @Autowired
	  FilesStorageService storageService;
	
	@Autowired
	private UserRepository userRepo;
	

	@Autowired
	private UserService service;
	
	@Autowired
	private JwtUtil util;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@PostMapping("/login")
	public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest request){

		//2.validate user and generate token
		Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				request.getEmail(), request.getPassword()));
		
		final UserDetailsInfo userDetails=(UserDetailsInfo) authentication.getPrincipal();
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
          String token=util.generateToken(userDetails);
		return ResponseEntity.ok(new UserResponse(userDetails.getUser(),token));
	}
	
	
	
	
	@RequestMapping(value = "/refreshtoken", method = RequestMethod.GET)
	public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws Exception {
		// From the HttpRequest get the claims
		DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");

		Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
		String token = util.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
		return ResponseEntity.ok(new AuthenticationResponse(token));
	}
	public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
		Map<String, Object> expectedMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}
	
	

	//3.checking the token
	@PostMapping("/welcome")
	public ResponseEntity<String> accessData(Principal p){
		UserDetailsInfo user = null;
		return ResponseEntity.ok("Hello User!"+p.getName());
	}
	
	//1. fetch all roles
		@GetMapping("/roles")
		public ResponseEntity<?> fetchAllUsers(){
			ResponseEntity<?> resp=null;
			try {
				List<Role> list=userService.listRoles();
				if(list.size() > 0 && !list.isEmpty()) {
					resp=new ResponseEntity<List<Role>>(list, HttpStatus.OK);
				}
				else {
					resp=new ResponseEntity<String>("Data not available", HttpStatus.INTERNAL_SERVER_ERROR);
				}
			
			} catch (Exception e) {
				resp=new ResponseEntity<String>("Unable to Fetch data", HttpStatus.INTERNAL_SERVER_ERROR);
				e.printStackTrace();
			}
			return resp;
		}
		
		
		
		
		
		@GetMapping("/all")
		public ResponseEntity<Map<String,Object>> listByPage(){
			  try {
			     List<User> list = userService.getAllUsers();
			     
			    list.forEach(System.out::println);
			     
			      Map<String, Object> response = new HashMap<>();
			      
			      
			      
			      
			      
			      response.put("success",true);
			      response.put("data",list);
			      return new ResponseEntity<>(response, HttpStatus.OK);
			    } catch (Exception e) {
			      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			    }
			  }
		
    //2.save all usesrs
	@PostMapping("/save")
	public ResponseEntity<?> getUserInfo(@RequestBody(required = true) User user) {
		System.out.println(user);
		ResponseEntity<?> resp = null;
	      
	try {
		if(user.getId()==null) {
//			Integer id=userService.saveUser(user);
			resp= new ResponseEntity<>(new UserResponse2("User is Saved succeffylly"),HttpStatus.OK);	
		}
		if(user.getId() !=null) {
			Integer userId=user.getId();
			if(userService.isUserExist(userId)) {
				userService.updateUser(user);
				resp=new ResponseEntity<String>("User Updated Successfully"+userId,HttpStatus.OK);
			}
			
		}
	} catch (Exception e) {
	throw new UserNotFoundException("User Already save "+user.getFirstName());
	}
	return resp;
	}
		
		 
		 
		 
		 
		  @GetMapping("/files/{filename:.+}")
		  @ResponseBody
		  public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		    Resource file = storageService.load(filename);
		    return ResponseEntity.ok()
		        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
		  }
	
	
	
	//3.get one user
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserDetails(@PathVariable(required = true) Integer id) {
		
		ResponseEntity<String> resp = null;

		try {
			User  userId= userService.getOneUser(id);
			if (userId!=null) {
				Map<String, Object> response = new HashMap<>();
				response.put("data", userId);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("No Student exist with id:"+id,HttpStatus.OK);
			}
			
		} catch (Exception e) {
			throw  new UserNotFoundException("please try after some time");	
		}
				
	}
	
	
	//3.fetch user data by email
	@GetMapping("/user/{email}")
	public ResponseEntity<?> fetchUser(@PathVariable String email) {

		ResponseEntity<String> resp = null;
		try {
			User  userId= userService.findByEmail(email);
			if (userId!=null) {
				return new ResponseEntity<User>(userId, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("No Student exist with id:"+email,HttpStatus.OK);
			}		
		} catch (Exception e) {
			throw  new UserNotFoundException("please try after some time");	
		}		
	}
	
	
	//4.delete one user
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Integer id){
		
		ResponseEntity<String> resp=null;
		try {
			
			if(userService.isUserExist(id)) {
				userService.deleteById(id);
				resp=new ResponseEntity<String>("Record Deleted = "+ id,HttpStatus.OK);
			}else {
				resp=new ResponseEntity<String>("Record not exist by this id::"+id,HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			throw new UserNotFoundException("please try after sometime");
		}
		
		return resp;
	}
	
	
	//5.update one user
	@PutMapping
	public ResponseEntity<String> updateUser(@RequestBody User user){
		ResponseEntity<String> resp = null;
		//find the user exist or not 
		Integer userId=user.getId();
		try {
			if(userService.isUserExist(userId)) {
				userService.updateUser(user);
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
	
	 private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
	        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
	                message), httpStatus);
	    }	
}
