package com.ps;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import com.ps.model.Category;
import com.ps.repo.CategoryRepository;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)

public class CategoryTestRepository {

	@Autowired
	private CategoryRepository repo;

//	@Test
//	@Disabled
//	public void testCreateCategory() {
//
//		Category category = new Category("Electronic");
//		Category savedCategory = repo.save(category);
//		assertThat(savedCategory.getId()).isGreaterThan(0);	
//	}
//
//		@Test
//		@Disabled
//		public void testCreateSubCategorys() {
//			
//			Category parent=new Category(2);
//			Category phone=new Category("Computer",parent);
//			
//			Category smartPhone=new Category("Lapto", parent);
//			Category curd=new Category("Desktop", parent);
//			Category cable=new Category("Ipad", parent);
//			List<Category> categoryList = Arrays.asList(smartPhone,phone,curd,cable);
//			repo.saveAll(categoryList);
//			
//				/*repo.saveAll(List.of(camera,phone));*/
//		}

//	@Test
//	@Disabled
//	public void testCreateSubCategory() {
//	
//		Category parent = new Category(5);
//		Category milk = new Category("Old Curd", parent);
//		Category milkFresh = new Category("Fresh Curd", parent);
//		List<Category> categoryList = Arrays.asList(milk ,milkFresh);
//		repo.saveAll(categoryList);
//		assertThat( categoryList).isNotNull();
//	}
	
	@Test
	@Disabled
	public void getTheTestCategory() {
		Category category = repo.findById(1).get();
		System.out.println(category.getName());
		Set<Category> childern = category.getChildern();
		for (Category subCategory : childern) {
			System.out.println(subCategory.getName());
		}
		assertThat(childern.size()).isGreaterThan(0);
	}
	

		@Test
		public void testPrintHierarichalCategories() {
			Iterable<Category> categories=repo.findAll();
			for(Category category:categories) {
				if(category.getParent()==null) {
					System.out.println(category.getName());
					Set<Category> childern = category.getChildern();
					for(Category subCategory:childern) {
						System.out.println("--"+subCategory.getName());
						printChildern(subCategory, 1);
						
					}
				}}}

	private void printChildern(Category parent,int sublevel) {
		int newSubLevel=sublevel + 1;            
		Set<Category> childern = parent.getChildern();
		
		for(Category subCategory:childern) {
			for(int i=0;i<newSubLevel;i++) {
				System.out.println("----");
			}
			System.out.println(subCategory.getName());
			printChildern(subCategory, newSubLevel);
		}
	}

		@Test
		@Disabled
		public void rootCategoryTest() {
			List<Category> list=repo.findRootCategory(Sort.by("name").ascending());
			list.forEach(cat->System.out.println(cat.getName()));
		}
		
		@Test
		@Disabled
		public void testFindByNameCategory() {
			
			String name="Computer";
			Category category=repo.findByName(name);
			System.out.println(category);
			
			assertThat(category).isNotNull();
			assertThat(category.getName()).isEqualTo(name);
		}

		
	@Test
	@Disabled
	public void testFindByNameAlias() {
		
		String name="Computer";
		Category category=repo.findByAlias(name);
		System.out.println(category);
		
		assertThat(category).isNotNull();
		assertThat(category.getAlias()).isEqualTo(name);
	}
	
}
