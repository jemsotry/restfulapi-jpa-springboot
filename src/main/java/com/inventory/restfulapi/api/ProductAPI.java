package com.inventory.restfulapi.api;

import com.inventory.restfulapi.model.Product;
import com.inventory.restfulapi.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;


@RestController
public class ProductAPI {
	
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final ProductService productService;

    @Autowired
    public ProductAPI(ProductService stockService) {
        this.productService = stockService;
    }

    @GetMapping("/api/v1/products")
    public ResponseEntity<Map<Long, Product>> findAll() {
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
}
