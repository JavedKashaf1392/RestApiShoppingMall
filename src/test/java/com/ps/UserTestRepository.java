package com.ps;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.apache.poi.ss.formula.functions.Replace;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import com.ps.model.Role;
import com.ps.model.User;
import com.ps.repo.UserRepository;



@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserTestRepository {
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	


	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		
		User user = new User("javed@gmail.com", "javed", "mohammad javed", 343434545,"javed","kashaf",true);		
		user.addRole(roleAdmin);
		User savedUser = repo.save(user);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
//	
//	@Test
//	public void testCreateNewUserWithTwoRoles() {
//		User userRavi = new User("ravi@gmail.com", "ravi2020", "Ravi", "Kumar");
//		Role roleEditor = new Role(3);
//		Role roleAssistant = new Role(5);
//		
//		userRavi.addRole(roleEditor);
//		userRavi.addRole(roleAssistant);
//		
//		User savedUser = repo.save(userRavi);
//		
//		assertThat(savedUser.getId()).isGreaterThan(0);
//	}
//	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}
//	
//	@Test
//	public void testGetUserById() {
//		User userNam = repo.findById(1).get();
//		System.out.println(userNam);
//		assertThat(userNam).isNotNull();
//	}
//	
//	@Test
//	public void testUpdateUserDetails() {
//		User userNam = repo.findById(1).get();
//		userNam.setEnabled(true);
//		userNam.setEmail("namjavaprogrammer@gmail.com");
//		
//		repo.save(userNam);
//	}
//	
//	@Test
//	public void testUpdateUserRoles() {
//		User userRavi = repo.findById(2).get();
//		Role roleEditor = new Role(3);
//		Role roleSalesperson = new Role(2);
//		
//		userRavi.getRoles().remove(roleEditor);
//		userRavi.addRole(roleSalesperson);
//		
//		repo.save(userRavi);
//	}
//	
//	@Test
//	public void testDeleteUser() {
//		Integer userId = 2;
//		repo.deleteById(userId);
//		
//	}
//	
//	@Test
//	public void testGetUserByEmail() {
//		String email = "ravi@gmail.com";
//		User user = repo.getUserByEmail(email);
//		
//		assertThat(user).isNotNull();
//	}
//	
//	@Test
//	public void testCountById() {
//		Integer id = 1;
//		Long countById = repo.countById(id);
//		
//		assertThat(countById).isNotNull().isGreaterThan(0);
//	}
//	
//	@Test
//	public void testDisableUser() {
//		Integer id = 1;
//		repo.updateEnabledStatus(id, false);
//		
//	}
//	
//	@Test
//	public void testEnableUser() {
//		Integer id = 3;
//		repo.updateEnabledStatus(id, true);
//		
//	}	
//	
//	@Test
//	public void testListFirstPage() {
//		int pageNumber = 0;
//		int pageSize = 4;
//		
//		Pageable pageable = PageRequest.of(pageNumber, pageSize);
//		Page<User> page = repo.findAll(pageable);
//		
//		List<User> listUsers = page.getContent();
//		
//		listUsers.forEach(user -> System.out.println(user));
//		
//		assertThat(listUsers.size()).isEqualTo(pageSize);
//	}
//	
//	@Test
//	public void testSearchUsers() {
//		String keyword = "bruce";
//	
//		int pageNumber = 0;
//		int pageSize = 4;
//		
//		Pageable pageable = PageRequest.of(pageNumber, pageSize);
//		Page<User> page = repo.findAll(keyword, pageable);
//		
//		List<User> listUsers = page.getContent();
//		
//		listUsers.forEach(user -> System.out.println(user));	
//		
//		assertThat(listUsers.size()).isGreaterThan(0);
//	}

}
