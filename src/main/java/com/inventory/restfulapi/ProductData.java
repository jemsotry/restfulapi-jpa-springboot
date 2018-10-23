package com.inventory.restfulapi;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.inventory.restfulapi.model.Product;

public enum ProductData {
	
    INSTANCE;

    private Map<Long, Product> products;

    public void setProducts(Map<Long, Product> products) {
		this.products = products;
	}

	public void initData() {
    	
        products = new HashMap<>();

//        for (int i = 1; i <= 10; i++) {
//            Product newProduct = new Product("S" + i, new BigDecimal(i), "");
//            products.put(newProduct.getId(), newProduct);
//        }
    }

    public Map<Long, Product> getProducts(){
        return products;
    }
}
