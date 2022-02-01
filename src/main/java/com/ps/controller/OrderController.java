package com.ps.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ps.model.Order;
import com.ps.model.Product;
import com.ps.repo.OrderRepository;
import com.ps.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderRepository orderRepo;
	
	
	//1.Fetch All Data is here
		@GetMapping("/orders")
		public ResponseEntity<Map<String,Object>> getAllData(){
			  
		    try {
		    	String email="afasa@test.com";
		      List<Order> allOrders = orderRepo.getByCustomerEmail(email);
		      System.out.print("allOrders"+allOrders);
		      Map<String, Object> response = new HashMap<>();
		      response.put("list",allOrders);
		      return new ResponseEntity<>(response, HttpStatus.OK);
		    } catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		    }
		  }

}
