package com.ps.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.ps.dto.PaymentInfo;
import com.ps.dto.Purchase;
import com.ps.dto.PurchaseResponse;
import com.ps.model.Customer;
import com.ps.model.Order;
import com.ps.model.OrderItem;
import com.ps.repo.CustomerRepository;
import com.ps.service.CheckoutService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import lombok.Value;

@Service
public class CheckoutServiceImpl implements CheckoutService{
	

	@Autowired
    private CustomerRepository customerRepository;
//	
//	 public CheckoutServiceImpl(@Value("${stripe.key.secret}") String secretKey,CustomerRepository customerRepository) {
//
//     this.customerRepository = customerRepository;

//     // initialize Stripe API with secret key
//Stripe.apiKey = secretKey;
//}
  
	@Override
	@Transactional
	public PurchaseResponse placeOrder(Purchase purchase) {
		
		//generate th eorder info from dto
		Order order=purchase.getOrder();
		//generate tracking number
		String orderTrackingNumber = generateOrderTrackingNumber();
		order.setOrderTrackingNumber(orderTrackingNumber);
		
		//populate the order with orderItems
		Set<OrderItem> orderItems = purchase.getOrderItems();
		orderItems.forEach(item -> order.add(item));
		//populate order with billingAddressed and shipping
		
		order.setBillingAddress(purchase.getBillingAddress());
		order.setShippingAddress(purchase.getShippingAddress());
		
		
		//populate customer with order 
		Customer customer=purchase.getCustomer();
		customer.add(order);
		
		//save to database 
		customerRepository.save(customer);
		
		//return a response
		
		return new PurchaseResponse(orderTrackingNumber);
	}

	private String generateOrderTrackingNumber() {
		//generate a random UUID number (UUID version-4)
		//for details see
		return UUID.randomUUID().toString();
	}

	@Override
	public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {
		List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfo.getAmount());
        params.put("currency", paymentInfo.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);
        params.put("description", "Luv2Shop purchase");
//        params.put("receipt_email", paymentInfo.getReceiptEmail());
		
		
		return PaymentIntent.create(params);
	}
	
	
	

}
