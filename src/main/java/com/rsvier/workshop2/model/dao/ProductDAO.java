package com.rsvier.workshop2.model.dao;

import java.util.List;

import com.rsvier.workshop2.model.Product;

public interface ProductDAO {

	//Create
	public int createProduct(Product product);
	
	// Read
	public List<Product> findAllProducts();
	public Product findProductById(Long productId);
	public Product findProductByName(String name);
	public boolean isProductStoredWithId(Long productId);
	
	// Update
	public boolean updateProduct(Product product);
	
	// Delete
	public boolean deleteProduct(Product product);
	
}
