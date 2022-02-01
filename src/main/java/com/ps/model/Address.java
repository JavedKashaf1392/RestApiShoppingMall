package com.ps.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="Address")
@Getter
@Setter
public class Address {
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="doorAndStree")
	private String doorAndStree;
	
	@Column(name="AreaAndLocality")
	private String AreaAndLocality;
	
	@Column(name="landMark")
	private String landMark;
	
	@Column(name="City")
	private String City;
	
	@Column(name="State")
	private String State;
	
	@Column(name="Country")
	private String country;
	
	
	@Column(name="Pincode")
	private String Pincode;
	
	@OneToOne
	@PrimaryKeyJoinColumn()
	private Order order;
	
	
	

}
