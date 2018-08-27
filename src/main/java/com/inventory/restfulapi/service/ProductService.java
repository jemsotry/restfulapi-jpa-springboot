package com.inventory.restfulapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.restfulapi.model.Product;
import com.inventory.restfulapi.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
		
    public List<Product> findAll() {
    	return productRepository.findAll();
    }

    public Optional<Product> findProductById(Long id) {
    	return Optional.ofNullable(productRepository.findOne(id));
   }

    public Product saveProduct(Product product) {
    	return productRepository.save(product);
    }

	public void delete(Long productId) {
		productRepository.delete(productId);
	}
}
