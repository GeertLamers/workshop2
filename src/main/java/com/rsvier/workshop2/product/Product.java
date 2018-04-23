package com.rsvier.workshop2.product;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {

	@Id
	@Column(name = "id", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	private String name;
	private BigDecimal price;
	
	@Column(name = "stock_quantity")
	private int stockQuantity;
	
	@Column(name = "produced_year")
	private int producedYear;
	private String country;
	
	@Column(name = "grape_variety")
	private String grapeVariety;
	
	@Column(name = "alcohol_percentage")
	private double alcoholPercentage;

	public Product() {
	}

	// Constructor with basic params
	public Product(String productName,
				   BigDecimal price,
				   int stockQuantity) {

		this.name = productName;
		this.price = price;
		this.stockQuantity = stockQuantity;
	}

	// Constructor with all params
	public Product(String productName,
				   BigDecimal price,
				   int stockQuantity,
				   int producedYear,
				   String country,
				   String grapeVariety,
				   double alcoholPercentage) {

		this.name = productName;
		this.price = price;
		this.stockQuantity = stockQuantity;
		this.producedYear = producedYear;
		this.country = country;
		this.grapeVariety = grapeVariety;
		this.alcoholPercentage = alcoholPercentage;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String productName) {
		this.name = productName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public int getProducedYear() {
		return producedYear;
	}

	public void setProducedYear(int producedYear) {
		this.producedYear = producedYear;
	}

	public double getAlcoholPercentage() {
		return alcoholPercentage;
	}

	public void setAlcoholPercentage(double alcoholPercentage) {
		this.alcoholPercentage = alcoholPercentage;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getGrapeVariety() {
		return grapeVariety;
	}

	public void setGrapeVariety(String grapeVariety) {
		this.grapeVariety = grapeVariety;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + name + ", price=" + price
				+ ", stockQuantity=" + stockQuantity + ", producedYear=" + producedYear + ", country=" + country
				+ ", grapeVariety=" + grapeVariety + ", alcoholPercentage=" + alcoholPercentage + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((grapeVariety == null) ? 0 : grapeVariety.hashCode());
		result = prime * result + producedYear;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	/* Products are considered equal when their names, country, grape variety, production year all match */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (grapeVariety == null) {
			if (other.grapeVariety != null)
				return false;
		} else if (!grapeVariety.equals(other.grapeVariety))
			return false;
		if (producedYear != other.producedYear)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
