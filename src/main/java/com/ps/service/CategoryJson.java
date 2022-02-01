package com.ps.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ps.model.Category;
import com.ps.model.User;

@Service
public class CategoryJson {
	public static Category getJson(String category) {

		Category CategoryJson = new Category();
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			CategoryJson = objectMapper.readValue(category, Category.class);
		} catch (IOException err) {
			System.out.printf("Error Accured please check it");
		}
		return CategoryJson;
	}
}



