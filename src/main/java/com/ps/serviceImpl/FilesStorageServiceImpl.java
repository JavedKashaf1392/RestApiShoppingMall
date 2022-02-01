package com.ps.serviceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ps.service.FilesStorageService;

@Service
public class FilesStorageServiceImpl implements FilesStorageService{
	
	private final Path root = Paths.get("");

	@Override
	public void init() {
		 try {
		      Files.createDirectory(root);
		    } catch (IOException e) {
		      throw new RuntimeException("Could not initialize folder for upload!");
		    }		
	}
	
	
	@Override
	public void save(String uploadDir,String fileName,MultipartFile multipartfile) throws IOException{
               
		Path uploadPath=Paths.get(uploadDir);
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		try(InputStream inputStream = multipartfile.getInputStream()) {
			Path filePath=uploadPath.resolve(fileName);
			Files.copy(inputStream,filePath,StandardCopyOption.REPLACE_EXISTING);
			
			
		}catch (IOException ex) {
			throw new IOException("Could not save file :"+ fileName,ex);
		}
		
		
		
	}

//	@Override
//	public Resource load(String filename) {
//		try {
//		      Path file = root.resolve(filename);
//		      Resource resource = new UrlResource(file.toUri());
//
//		      if (resource.exists() || resource.isReadable()) {
//		        return resource;
//		      } else {
//		        throw new RuntimeException("Could not read the file!");
//		      }
//		    } catch (MalformedURLException e) {
//		      throw new RuntimeException("Error: " + e.getMessage());
//		    }
//	}
	
	
	  @Override
	  public Resource load(String filename) {
	    try {
	      Path file = root.resolve(filename);
	      Resource resource = new UrlResource(file.toUri());

	      if (resource.exists() || resource.isReadable()) {
	        return resource;
	      } else {
	        throw new RuntimeException("Could not read the file!");
	      }
	    } catch (MalformedURLException e) {
	      throw new RuntimeException("Error: " + e.getMessage());
	    }
	  }

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(root.toFile());
		
	}

	@Override
	public Stream<Path> loadAll() {
		   try {
			      return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
			    } catch (IOException e) {
			      throw new RuntimeException("Could not load the files!");
			    }
			  }
}
