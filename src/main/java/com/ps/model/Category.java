package com.ps.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name="categories")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Category_id")
	private Integer id;

	@Column(length = 128,nullable = false,unique = true)
	private String name;
	
	@Column(length = 128,unique = true)
	private String alias;
	
	@Column(length = 128,nullable = false)
	private String image;
	
	private boolean enabled=true;
	
	@Column(name = "all_parent_ids", length = 256)
	private String allParentIDs;
		
	
	@OneToOne
	@JoinColumn(name="parent_id")
	private Category parent;
	
	@OneToMany(mappedBy = "parent")
	@JsonIgnore
	private Set<Category> childern=new HashSet<>();
	
	public Category() {
	}
	
	public Category(Integer id) {
		this.id = id;
	}
	
	
	//first adding in the queue
	public static Category fullCopy(Category category) {
		Category copyCategory=new Category();
		copyCategory.setId(category.getId());
		copyCategory.setName(category.getName());
		copyCategory.setAlias(category.getAlias());
		copyCategory.setImage(category.getImage());
		copyCategory.setEnabled(category.isEnabled());
		copyCategory.setHasChildren(category.getChildern().size() > 0);
		return copyCategory;
	}
	

	//for the second this is copy
	public static Category fullCopy(Category category,String name,Integer id) {
		Category copyCategory=new Category();
//		copyCategory.setId(category.getId());
		copyCategory.setName(category.getName());
		copyCategory.setAlias(category.getAlias());
		copyCategory.setImage(category.getImage());
		copyCategory.setEnabled(category.isEnabled());
		copyCategory.setHasChildren(category.getChildern().size() > 0);
		copyCategory.setId(id);
		copyCategory.setName(name);
	return copyCategory;
	}
	
	public static Category fullCopy(Category category,String name) {
		Category copyCategory=Category.fullCopy(category);
		copyCategory.setName(name);
	return copyCategory;
	}
	
	
	
	
	
	
	public static Category copyIdAndName(Category category) {
		Category copyCategory = new Category();
		copyCategory.setId(category.getId());
		copyCategory.setName(category.getName());
		copyCategory.setAlias(category.getAlias());
		copyCategory.setEnabled(category.enabled);
		copyCategory.setImage(category.getImage());
		return copyCategory;
	}

	public static Category copyData(Integer id, String name,String alias,boolean enabled,String image) {
		Category copyCategory = new Category();
		copyCategory.setId(id);
		copyCategory.setName(name);
		copyCategory.setAlias(alias);
		copyCategory.setEnabled(enabled);
		copyCategory.setImage(image);
		return copyCategory;
	}
	
	
	public Category(String name) {
		this.name = name;
		this.alias = name;
		this.image = "default.png";
	}
	
	public Category(String name, Category parent) {
		this(name);
		this.parent = parent;
	}	

	public Category(Integer id, String name, String alias) {
		super();
		this.id = id;
		this.name = name;
		this.alias = alias;
	}
	
	
	
	
	
	


	@Transient
	@JsonIgnore
	private boolean hasChildren;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getAllParentIDs() {
		return allParentIDs;
	}

	public void setAllParentIDs(String allParentIDs) {
		this.allParentIDs = allParentIDs;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public Set<Category> getChildern() {
		return childern;
	}

	public void setChildern(Set<Category> childern) {
		this.childern = childern;
	}

	public boolean isHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", alias=" + alias + ", image=" + image + ", enabled="
		+ enabled + ", parent=" + parent + "]";
	}

	
}

