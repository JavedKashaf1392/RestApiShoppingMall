package com.ps.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ps.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class SecurityFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;
	

//	@Override
//	protected void doFilterInternal(HttpServletRequest request, 
//			HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		
//		String token=request.getHeader("Authorization");
//		if(token!=null) {
//
//			String username=jwtUtil.getUsername(token);
//			
//			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
//				UserDetails user=userDetailsService.loadUserByUsername(username);
//				boolean isValid=jwtUtil.validateToken(token, user);
//				if(isValid) {
//					UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(username, user.getPassword(), user.getAuthorities());
//					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//					SecurityContextHolder.getContext().setAuthentication(authToken);
//				}
//			}
//		}
//		filterChain.doFilter(request, response);
//	}	        
//	}
	
	
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			
			String token=request.getHeader("Authorization");
			if(token!=null) {

				String username=jwtUtil.getUsername(token);
				
				if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
					UserDetails user=userDetailsService.loadUserByUsername(username);
					boolean isValid=jwtUtil.validateToken(token, user);
					if(isValid) {
						UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(username, user.getPassword(), user.getAuthorities());
						authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(authToken);
					}
				}
			}
			
		}catch (ExpiredJwtException ex) {
			
			String isRefreshToken = request.getHeader("isRefreshToken");
			String requestURL = request.getRequestURL().toString();
			// allow for Refresh Token creation if following conditions are true.
			if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refreshtoken")) {
				allowForRefreshToken(ex, request);
			} else
				request.setAttribute("exception", ex);

		} catch (BadCredentialsException ex) {
			request.setAttribute("exception", ex);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		filterChain.doFilter(request, response);
	}	        
	
	
	private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {

		// create a UsernamePasswordAuthenticationToken with null values.
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				null, null, null);
		// After setting the Authentication in the context, we specify
		// that the current user is authenticated. So it passes the
		// Spring Security Configurations successfully.
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		// Set the claims so that in controller we will be using it to create
		// new JWT
		request.setAttribute("claims", ex.getClaims());

	}
	}








