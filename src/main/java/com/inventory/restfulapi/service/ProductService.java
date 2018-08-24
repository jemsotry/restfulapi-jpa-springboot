package com.inventory.restfulapi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.restfulapi.ProductData;
import com.inventory.restfulapi.model.Product;
import com.inventory.restfulapi.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
    private Map<Long, Product> products;
	
    public Map<Long, Product> findAll() {
    	products = new HashMap<>();
    	
    	List<Product> result = productRepository.findAll();
    	for (int i=0; i<result.size(); i++) {
    		Product newProduct = new Product(result.get(i).getName(), result.get(i).getCurrentPrice());
            products.put(newProduct.getId(), newProduct);
    	}
    	if (products.size() == 0) {
    		return ProductData.INSTANCE.getProducts();
    	} else {
    		return products;
    	}
    }

    public Optional<Product> findProductById(Long id) {
    	return Optional.ofNullable(productRepository.findOne(id));
   }

    public Product saveProduct(Product product) {
    	return productRepository.save(product);
    }
}
