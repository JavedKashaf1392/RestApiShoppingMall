package com.ps.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ps.config.CategoryPageInfo;
import com.ps.exception.CategoryNotFoundException;
import com.ps.model.Category;
import com.ps.model.Product;
import com.ps.model.User;
import com.ps.repo.CategoryRepository;
import com.ps.repo.DeleteCategoryRepo;

@Service
public class CategoryService implements CategoryServiceI{
	
	
	@Autowired
	private CategoryRepository repo;
	
	private DeleteCategoryRepo categoryRepo;
	
	public List<Category> listAll(CategoryPageInfo pageInfo,int pageNum,int size,String keyword){
		Pageable pageable=PageRequest.of(pageNum,size);
		
		 Page<Category> pageCategories = null;
		
		if(keyword !=null && !keyword.isEmpty()) {
			pageCategories=repo.search(keyword,pageable,size);
		
	}else {
		pageCategories=repo.findRootCategory(pageable,size);
		System.out.println(pageCategories);
	}
		
		List<Category> rootCategories = pageCategories.getContent();
		pageInfo.setTotalElements(pageCategories.getTotalElements());
		pageInfo.setTotalPages(pageCategories.getTotalPages());
		
		if (keyword != null && !keyword.isEmpty()) {
			List<Category> searchResult = pageCategories.getContent();
			for (Category category : searchResult) {
				category.setHasChildren(category.getChildern().size() > 0);
			}
			return searchResult;
		} else {
			return listHierarichalCategories(rootCategories);
		}}
	
	private List<Category> listHierarichalCategories(List<Category> rootCategories){
		
		List<Category> hierarichalCategories=new ArrayList<>();
		for(Category rootCategory:rootCategories) {
			hierarichalCategories.add(Category.fullCopy(rootCategory));
			
			Set<Category> childern=sortSubCategories(rootCategory.getChildern());
			for(Category subCategory:childern) {
				String name="--"+subCategory.getName();
				Integer id=subCategory.getId();
				System.out.println("SubCategory Id"+id);
				hierarichalCategories.add(Category.fullCopy(rootCategory, name,id));		
				listSubhHierarichalCategories(hierarichalCategories,subCategory,1);
			}}
		return hierarichalCategories;	
	}
	
	
	private void listSubhHierarichalCategories(List<Category> hierarichalCategories
			,Category parent,int subLevel) {
		Set<Category> childern=sortSubCategories(parent.getChildern());
		int newSubLevel=subLevel+1;
		
		for(Category subCategory:childern) {
			String name="";
			for(int i=0;i<newSubLevel;i++) {
				name+="--";
			}
			name+=subCategory.getName();
			hierarichalCategories.add(Category.fullCopy(subCategory, name));
			listSubhHierarichalCategories(hierarichalCategories,subCategory,newSubLevel);
		}
	}
	
	
	
	//update category
	public Category get(Integer id) throws CategoryNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new CategoryNotFoundException("Could not find any category with ID"+id);
		}
	}
	
	
//	2.Root Category in forms
	public List<Category> CategoreiesInForm() {
		List<Category> categoryUsedInForm=new ArrayList<>();
		Iterable<Category> categoriesInDB = repo.findRootCategory(Sort.by("name").ascending());
		for(Category category:categoriesInDB) {
			if(category.getParent()==null) {
				categoryUsedInForm.add(Category.fullCopy(category));
				System.out.println(category.getName());
				Set<Category> childern = sortSubCategories(category.getChildern());
				for(Category subCategory:childern) {
					String name="--"+subCategory.getName();
					categoryUsedInForm.add(Category.copyData(subCategory.getId(),name,subCategory.getAlias(),subCategory.isEnabled(),subCategory.getImage()));
					listChildern(categoryUsedInForm,subCategory, 1);
				}}}
		return categoryUsedInForm;
	}
	
		
		private void listChildern(List<Category> categoryUsedInForm,Category parent,int sublevel) {
			int newSubLevel=sublevel + 1;     
			Set<Category> childern = sortSubCategories(parent.getChildern());
			for(Category subCategory:childern) {
				String name="";
				for(int i=0;i<newSubLevel;i++) {
					name+="--";
				}
				name+=subCategory.getName();
				categoryUsedInForm.add(Category.copyData(subCategory.getId(),name,subCategory.getAlias(),subCategory.isEnabled(),subCategory.getImage()));
				/*System.out.println(subCategory.getName());*/
				listChildern(categoryUsedInForm,subCategory, newSubLevel);
				}}

		//3.save category
		public Category createCategory(Category category) {
			Category parent = category.getParent();			
			if (parent != null) {
				String allParentIds = parent.getAllParentIDs() == null ? "-" : parent.getAllParentIDs();
				allParentIds += String.valueOf(parent.getId()) + "-";
				category.setAllParentIDs(allParentIds);
			}
			return repo.save(category);
		}
		
		public String checkUnique(Integer id,String name,String alias) {
			boolean isCreatingNew = (id== null || id==0);
			Category categoryByName=repo.findByName(name);
			if(isCreatingNew) {
				if(categoryByName !=null) {
					return "DuplicateName";
				}
				else {
					Category categoryByAlias=repo.findByAlias(alias);
					if(categoryByAlias!=null) {
						return "DuplicateAlias";
					}
				}
			}
				else {
					if(categoryByName !=null && categoryByName.getId() != id) {
						return "DuplicateName";
					}
					Category categoryByAlias=repo.findByAlias(alias);
					if(categoryByAlias !=null && categoryByAlias.getId() !=id) {
						return "DuplicateAlias";
					}
				}
			return "OK";
		}
		
		private SortedSet<Category> sortSubCategories(Set<Category> childern){
		          return sortSubCategories(childern,"asc");
		}
		
		private SortedSet<Category> sortSubCategories(Set<Category> childern,String sortDir){
			SortedSet<Category> sortedChilder=new TreeSet<>(new Comparator<Category>() {

				@Override
				public int compare(Category cat1, Category cat2) {
					if(sortDir.equals("asc")) {
						return cat1.getName().compareTo(cat2.getName());	
					}else {
						return cat2.getName().compareTo(cat1.getName());	
					}
					
				}
			});
			 sortedChilder.addAll(childern);
			return sortedChilder;
		}
		
		
		
		//enabel the states
		
		/*public void updateCategoryEnabledStatus(Integer id, boolean enabled) {
			repo.updateEnabledStatus(id, enabled);
		} */ 
		
			
		//8.GEt one product
		@Override
		public Category getCategory(Integer id) {
			Category category=null;
			Optional<Category> opt=repo.findById(id);
			if(opt.isPresent()) {
				category=opt.get();
			}
			return category;
		}

		@Override
		public boolean isCategoryExist(Integer id) {
			repo.existsById(id);
			return true;
		}

		@Override
		public Integer updateCategory(Category category) {
			return repo.save(category).getId();
		}

		@Override
		public Category getCategoryId(Integer id) {
			return repo.getCategoryId(id);
		}
		
		@Override
		public Integer saveCategory(Category category) {
			
			return repo.save(category).getId();
		}

		@Override
		public void deleteById(Integer id) {
			repo.deleteById(id);
		}
		
		
		@Override
		public List<Category> getByMultipleIds(Integer[] ids) {
			List<Category> list=categoryRepo.findByIdIn(ids);
			return list;
		}
}