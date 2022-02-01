package com.ps;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.isA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ps.model.Product;
import com.ps.service.ProductService;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
@DisplayName("PRODUCT TEST SERVICE")
@TestMethodOrder(OrderAnnotation.class)


public class ProductsTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ProductService service;
	
	
	@Test
	@DisplayName("FETCH ALL DATA")
	@Order(2)
	@Disabled
	public void testGetAll() throws Exception {
		
		//1.Create Request 
		MockHttpServletRequestBuilder requestBuilder=MockMvcRequestBuilders.get("/product");
		
		//#2.Execution of Request
		MvcResult result=mockMvc.perform(requestBuilder).andReturn();
		
		//#3 GET Result Response
		MockHttpServletResponse resp=result.getResponse();
		
		//print the reuslt which is coming from the request
		System.out.println("Response ::"+resp.getContentAsString());
		
		/*int status=resp.getStatus();
		assertEquals(200, status);*/
		
		
		//#4.Assert Result
		assertEquals(HttpStatus.OK.value(), resp.getStatus());
		assertNotNull(resp.getContentAsString());
		
	}
	
	
	@Test
	@Order(3)
	@DisplayName("GET USER TEST")
	@Disabled
	public void testGetUser() throws Exception {
		
		//#1.Create Object for MockHttpServletRequest Builder
		MockHttpServletRequestBuilder requestBuilder=MockMvcRequestBuilders.get("/product/1");
		
		//#2.Execute Request
		MvcResult result=mockMvc.perform(requestBuilder).andReturn();
		
		//#3.Get Response From the get One
		MockHttpServletResponse resp=result.getResponse();
		
		System.out.println("Response ::"+resp.getContentAsString());
		
		//#4.compare with assert
		assertEquals(HttpStatus.OK.value(), resp.getStatus());
		assertNotNull(resp.getContentAsString());
	}
	
	
	
	@Order(1)
	@DisplayName("Save Date Product")
	@Test
	public void saveProduct() throws Exception {
		
		Product prod=new Product();
		prod.setAvailable(true);
		prod.setProductCategory("Dairy");
		prod.setDescription("Its good man");
		prod.setFinalPrice(1000);
		prod.setProductName("Curd");
		prod.setProductPrice(95);
		prod.setProductQyt(3);
		prod.setSize(3);
		prod.setTax(2);
		
		
		ObjectMapper ob=new ObjectMapper();
		String json=ob.writeValueAsString(prod);
		
		
		
		//#1.create Object for testing the proxy systems
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/product")
				.contentType(MediaType.APPLICATION_JSON).content(json);
				
		
		//#2.execute the object
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		//#3.get Response from there
		MockHttpServletResponse resp = result.getResponse();
		
		System.out.println("Response the data ::"+resp.getContentAsString());
		
		//#4.checking with Assert
		assertEquals(HttpStatus.OK.value(),resp.getStatus());
		assertNotNull(resp.getContentAsString());
		if(!resp.getContentAsString().contains("saved with id")) {
			fail("Product not Saved Properly");
		}
	}
	
	
	
	
	@Order(4)
	@DisplayName("Update Date Product")
	@Test
	@Disabled
	public void UpdateProduct() throws Exception {
		
		Product prod=new Product();
		prod.setId(6);
		prod.setAvailable(true);
		prod.setProductCategory("Phone");
		prod.setDescription("This is  the worst Brand");
		prod.setFinalPrice(59999);
		prod.setProductName("Samsung");
		prod.setProductPrice(95);
		prod.setProductQyt(3);
		prod.setSize(3);
		prod.setTax(2);
		
		
		ObjectMapper ob=new ObjectMapper();
		String json=ob.writeValueAsString(prod);
		
		//#1.create Object for testing the proxy systems
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/product")
				.contentType(MediaType.APPLICATION_JSON).content(json);
				
		
		//#2.execute the object
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		//#3.get Response from there
		MockHttpServletResponse resp = result.getResponse();
		
		System.out.println("Response the data ::"+resp.getContentAsString());
		
		//#4.checking with Assert
		//4. assert result
				assertEquals(HttpStatus.OK.value(), resp.getStatus());
				assertNotNull(resp.getContentAsString());
				/*if(!resp.getContentAsString().contains("Product Updated!")) {
					fail("Product Not Updated Properly");
				}*/
			}
	
	
	
	@Order(5)
	@DisplayName("Delete Product")
	@Test
	@Disabled
	public void deleteProduct() throws Exception {
		MockHttpServletRequestBuilder requestBuilder=MockMvcRequestBuilders.delete("/product/6");
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse resp = result.getResponse();
		
		System.out.println("Response Data : "+resp.getContentAsString());
		
		assertEquals(HttpStatus.OK.value(), resp.getStatus());
		assertNotNull(resp.getContentAsString());	
	}
	
	
	
	
	
	
	
	
	
	
	
	//skip the method of the service

	@DisplayName("Save Date Product")
	@Test
	@Disabled
	public void saveProductWithController() throws Exception {
		
		Mockito.when(service.SaveProduct(Mockito.any(Product.class))).thenReturn(1212121212);
		
		
		Product prod=new Product();
		prod.setAvailable(true);
		prod.setProductCategory("Television");
		prod.setDescription("Its good man");
		prod.setFinalPrice(1000);
		prod.setProductName("Samsung Tv");
		prod.setProductPrice(95);
		prod.setProductQyt(3);
		prod.setSize(3);
		prod.setTax(2);
		
		
		ObjectMapper ob=new ObjectMapper();
		String json=ob.writeValueAsString(prod);
		
		
		
		//#1.create Object for testing the proxy systems
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/product")
				.contentType(MediaType.APPLICATION_JSON).content(json);
				
		
		//#2.execute the object
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		//#3.get Response from there
		MockHttpServletResponse resp = result.getResponse();
		System.out.println("Response the data ::"+resp.getContentAsString());
		
		//#4.checking with Assert
		assertEquals(HttpStatus.OK.value(),resp.getStatus());
		assertNotNull(resp.getContentAsString());
		if(!resp.getContentAsString().contains("saved with id")) {
			fail("Product not Saved Properly");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	}