package com.inventory.restfulapi.api;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.restfulapi.model.Product;
import com.inventory.restfulapi.service.ProductService;


@RestController
public class ProductAPI {
	
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final ProductService productService;

    @Autowired
    public ProductAPI(ProductService stockService) {
        this.productService = stockService;
    }

    @GetMapping("/api/v1/products")
    public ResponseEntity<List <Product>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @PostMapping("/api/v1/products")
    public ResponseEntity<Product> createANewProduct(@Valid @RequestBody Product product) {
        return ResponseEntity.ok(productService.saveProduct(product));
    }

    @GetMapping("/api/v1/products/{productId}")
    @ResponseBody
    public ResponseEntity<Product> findById(@PathVariable Long productId) {
        Optional<Product> productOptional = productService.findProductById(productId);
        if (!productOptional.isPresent()) {
            logger.severe("ProductId " + productId + " is not existed");
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(productOptional.get());
    }

    @PutMapping("/api/v1/products/{productId}")
    public ResponseEntity<Product> updatePriceOfAProduct(@PathVariable Long productId, BigDecimal currentPrice) {
        Optional<Product> productOptional = productService.findProductById(productId);
        if (!productOptional.isPresent()) {
            logger.severe("ProductId " + productId + " is not existed");
            ResponseEntity.badRequest().build();
        }

        Product product = productOptional.get();
        product.setCurrentPrice(currentPrice);

        return ResponseEntity.ok(productService.saveProduct(product));
    }
    
	@DeleteMapping("/api/v1/products/{productId}")
	public void delete(@PathVariable("productId") Long productId) {
		productService.delete(productId);
	}
}
