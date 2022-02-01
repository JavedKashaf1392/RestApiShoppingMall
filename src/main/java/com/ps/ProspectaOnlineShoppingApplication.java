package com.ps;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.ps.service.FilesStorageService;

@SpringBootApplication
public class ProspectaOnlineShoppingApplication implements CommandLineRunner{
	
	 @Resource
	 FilesStorageService storageService;
	
	public static void main(String[] args) {
		SpringApplication.run(ProspectaOnlineShoppingApplication.class, args);	
	}
	
	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config=new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedHeader("*");
//		config.addAllowedOrigin("*");
		config.addAllowedOriginPattern("*");
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("DELETE");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
		}

	@Override
	public void run(String... args) throws Exception {
//		 storageService.deleteAll();
//		  storageService.init();
		
	}
	}