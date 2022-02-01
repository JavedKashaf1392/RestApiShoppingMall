package com.ps.response;

import org.springframework.http.HttpHeaders;

import com.ps.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserResponse {
	
	
	
	private User user;
	public UserResponse(User user, String token) {
		super();
		this.user = user;
		this.token = token;
	}
	private String token;
//	private String message;

}
