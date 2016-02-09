package com.epam.periodicals.shared;

import java.io.Serializable;

public class Journal implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String description;
	private Long price;
	
	
	public Journal() {
		
	}
	
	public Journal(Long id, String name, String description, Long price) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
	}
	public Journal(String name, String description, Long price) {
		this.name = name;
		this.description = description;
		this.price = price;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrise(Long price) {
		this.price = price;
	}

	

}
