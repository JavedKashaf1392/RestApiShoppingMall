package com.ps;



import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ps.model.Product;

/*@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
@DisplayName("PRODUCT TEST SERVICE WITH INTEGRATION")
*/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProspectaOnlineShoppingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTesting {
	
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	HttpHeaders headers = new HttpHeaders();
	
	@LocalServerPort
	private int port;
	
	private String getRootUrl() {
		return "http://localhost:"+port;
	}
	
	@Test
	@Order(1)
	public void TestAllProducts() {
		
		HttpHeaders headers=new HttpHeaders();
		HttpEntity<String> entity= new HttpEntity<String>(null,headers);
		ResponseEntity<String> resp=restTemplate.exchange(getRootUrl()+ "/product", HttpMethod.GET,entity,String.class);
		
		System.out.println("Response Code::"+resp.getStatusCodeValue());
		System.out.println("Response Body::"+resp.getBody());
		assertNotNull(resp.getBody());	
	}
	
	
	//2.get Product by Id
	/*@Test
	@Order(1)*/
	public void testProdubById() {
		Product product=restTemplate.getForObject(getRootUrl() + "/product/1", Product.class);
		System.out.println(product.getProductName());
		assertNotNull(product);
	}
	
	//3.Save data to the Save
	
	@Test
	@Order(1)

	public void saveProductData() throws JsonProcessingException {
		
		Product prod=new Product();
		prod.setDescription("this is good");
		prod.setDiscount(2);
		prod.setFinalPrice(10000);
		prod.setProductCategory("Fruits");
		prod.setProductName("Mobile");
		prod.setProductPrice(4555000);
		prod.setProductQyt(3);
		prod.setSize(4);
		prod.setTax(4);
		
		
		 ResponseEntity<Product> postResponse = restTemplate.postForEntity(getRootUrl() + "/product", prod, Product.class);
	        
			// collect Response
		 int status = postResponse.getStatusCodeValue();
			Product resultProduct = postResponse.getBody();
			System.out.println(postResponse.getBody());

			// verify
			assertEquals("Incorrect Response Status", HttpStatus.CREATED.value(), status);
			assertNotNull(resultProduct);
			assertNotNull(resultProduct.getId().longValue());
		 
		/*	ResponseEntity<Product> postResp=restTemplate.postForEntity(getRootUrl() + "/product", prod, Product.class);
			
			HttpEntity<Product> entity=new HttpEntity<Product>(prod,headers);
			ResponseEntity<String> resp=restTemplate.exchange(getRootUrl() + "/product",HttpMethod.POST,entity,String.class);
			
			String actual=resp.getHeaders().get(HttpHeaders.LOCATION).get(0);
			assertTrue(actual.contains("/product/"));
			
			System.out.println(postResp.getBody());
			System.out.println(postResp.getStatusCodeValue());
			assertNotNull(postResp);
			assertNotNull(postResp.getBody());*/
		}
		
		/*	
			@Test
			@Order(1)*/
	
	@DisplayName("Update method Testing")
	public void UpdateProductData() throws JsonProcessingException {
		
		Product prod=new Product();
		
		Product product=restTemplate.getForObject(getRootUrl() + "/product/1", Product.class);
		
		System.out.println("the get DAta is here::"+product);
		
		prod.setDescription("this is good");
		prod.setDiscount(2);
		prod.setFinalPrice(10000);
		prod.setProductCategory("Fruits");
		prod.setProductName("Computer");
		prod.setProductPrice(4555000);
		prod.setProductQyt(3);
		prod.setSize(4);
		prod.setTax(4);
		
		 restTemplate.put(getRootUrl() + "/product" , prod, Product.class);
		 

		Product productUpdate=restTemplate.getForObject(getRootUrl() + "/product/1", Product.class);
		System.out.println("Data is her:::"+productUpdate);
	        assertNotNull(productUpdate);
	     
	}
	
	
	

//	 @Order(2)
//	
//	    public void testDeleteEmployee() {
//		 
//	         Product product = restTemplate.getForObject(getRootUrl() + "/product/4" , Product.class);
//	         assertNotNull(product);
//	         restTemplate.delete(getRootUrl() + "/employees/4");
//	         try {
//	              product = restTemplate.getForObject(getRootUrl() + "/employees/4", Product.class);
//	         } catch (final HttpClientErrorException e) {
//	              assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
//	         }
//	    }
//	
	
	
	
	}
