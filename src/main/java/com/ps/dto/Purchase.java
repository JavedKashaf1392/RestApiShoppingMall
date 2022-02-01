package com.ps.dto;

import java.util.Set;

import com.ps.model.Address;
import com.ps.model.Customer;
import com.ps.model.Order;
import com.ps.model.OrderItem;

import lombok.Data;

@Data
public class Purchase {
	
	private Customer customer;
	private Address shippingAddress;
	private Address billingAddress;
	private Order order;
	private Set<OrderItem> orderItems;
	
	
	

}
