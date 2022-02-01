package com.ps.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ps.model.Order;
import com.ps.model.User;
import com.ps.repo.OrderRepository;
import com.ps.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository orderRepo;

	
//	@Override
//	public List<Order> findByCustomerEmail() {
//		Order byCustomerEmail = orderRepo.getByCustomerEmail();
//		System.out.println("Customer data"+byCustomerEmail);
//		
//		return (List<Order>) byCustomerEmail ;
//	}

}
