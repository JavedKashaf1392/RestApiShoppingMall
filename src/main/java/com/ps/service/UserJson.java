package com.ps.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ps.model.User;

@Service
public class UserJson {
	public static User getJson(String user) {

		User userJson = new User();
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			userJson = objectMapper.readValue(user, User.class);
		} catch (IOException err) {
			System.out.printf("Error Accured please check it");
		}
		return userJson;
	}
}



