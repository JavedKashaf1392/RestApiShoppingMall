package com.ps.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ps.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{
	
//     @Query("FROM Order LEFT OUTER JOIN Customer ON Order.customer_id=Customer.id Customer.email ='mohammad.javed1392@gmail.com'")
    @Query("FROM Order ordr LEFT OUTER JOIN Customer cust ON ordr.customer = cust.id WHERE cust.email = :email")
	public List<Order> getByCustomerEmail(@Param("email") String email);

}
