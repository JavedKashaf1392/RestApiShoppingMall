package com.ps.model;

import java.util.Date;
import java.util.Locale.Category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PRODUCT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PRODUCT_ID")
	private Integer id;

	@Column(name = "PRODUCT_NAME")
	private String productName;

	@Column(name = "PRODUCT_PRICE")
	private double productPrice;

	@Column(name = "PRODUCT_QUANTITY")
	private double productQyt;

	@Column(name = "PRODUCT_DISCOUNT")
	private double discount;

	@Column(name = "IS_AVAAILABLE")
	private boolean isAvailable=true;

	@Transient
	private MultipartFile image;
	
	
	@Column(name = "PRODUCT_PHOTO")
	private String photo;

	@Column(name = "PRODUCT_CATEGORY")
	private String productCategory;
	
	@Column(name = "PRODUCT_DESCRIPTION",columnDefinition="text")
	private String description;

	@Column(name = "FINAL_PRICE")
	private double finalPrice;

	@Column(name = "PRODUCT_SIZE")
	private float size;
	
	@JsonIgnore
	@Column(name="View")
	private Integer view=0;

	@JsonIgnore
	@Column(name = "PRODUCT_TAX")
	private double tax;

	@JsonIgnore
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", updatable = false)
	private Date createDate;

	@JsonIgnore
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE", insertable = false)
	private Date updatedDate;

	public Product(Integer id, String productName, double productPrice, double productQyt, double discount,
			boolean isAvailable, String productCategory, String description, double finalPrice, float size,
			Integer view, double tax, Date createDate, Date updatedDate) {

		this.id = id;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productQyt = productQyt;
		this.discount = discount;
		this.isAvailable = isAvailable;
		this.productCategory = productCategory;
		this.description = description;
		this.finalPrice = finalPrice;
		this.size = size;
		this.view = view;
		this.tax = tax;
		this.createDate = createDate;
		this.updatedDate = updatedDate;
	}

	
	
	
	
	
	
	
	
	/*@ManyToOne
	@JoinColumn(name="categoryId")
	private Category category;*/
	
	
	
	

}
