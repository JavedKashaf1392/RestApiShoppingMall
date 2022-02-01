package com.ps.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.ps.model.FileInfo;
import com.ps.model.ResponseMessage;
import com.ps.model.User;
import com.ps.service.FilesStorageService;
import com.ps.service.UserJson;
import com.ps.service.UserService;

@RestController
@RequestMapping("/file")
public class FilesController {

	
	  @Autowired
	  FilesStorageService storageService;
	  
	  @Autowired
	  private UserService userService;
	  
	  
	  
	  
	  @PostMapping(value = "/upload", consumes = { MediaType.APPLICATION_JSON_VALUE,
			  MediaType.MULTIPART_FORM_DATA_VALUE })
			  public ResponseEntity<ResponseMessage> uploadFile1(@RequestParam("file") MultipartFile multipartFile,
					   @RequestPart("user") String userJson)  {
		  String message="";
		  Integer id = 0;
				  User user = UserJson.getJson(userJson);
				  String fileName=StringUtils.cleanPath(multipartFile.getOriginalFilename());
				  try {
				  if(user.getId()==null && !multipartFile.isEmpty()) {
					  
//					  Resource file = storageService.load(fileName);
					  
//					  System.out.println(file);
					  String uploadDir="user-photo";
					  user.setPhotos(fileName);
					  id=userService.saveUser(user);
				      storageService.save(uploadDir, fileName, multipartFile);
				  }else {
					  
				  }
			      message = "User Saved Succcessfully:" + id;
			      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			    } catch (Exception e) {
			      message = "Could not upload the file: "+multipartFile.getOriginalFilename() + "!";
			      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			    }
			  }
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	 
	  
	  
	  
	  @GetMapping("/files")
	  public ResponseEntity<List<FileInfo>> getListFiles() {
	    List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
	    	
	      String filename = path.getFileName().toString();
	      String url = MvcUriComponentsBuilder
	          .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();

	      return new FileInfo(filename, url);
	    }).collect(Collectors.toList());

	    return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
	  }

	  @GetMapping("/files/{filename:.+}")
	  @ResponseBody
	  public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		  
		  System.out.println();
	    Resource file = storageService.load(filename);
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	  }
	  
	  
	  
	  
}
