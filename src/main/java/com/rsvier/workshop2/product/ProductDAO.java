package com.rsvier.workshop2.product;

import java.util.List;

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
