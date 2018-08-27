package com.inventory.restfulapi;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.restfulapi.api.ProductAPI;
import com.inventory.restfulapi.model.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductAPI productAPI;

    @Test
    public void findAll() throws Exception {
    	Product Product = new Product("Nintendo Switch", new BigDecimal(255));
    	List<Product> allProducts = Arrays.asList(Product);
    	given(productAPI.findAll()).willReturn(ResponseEntity.ok(allProducts));
        this.mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(allProducts.size())));
    }

    @Test
    public void findById() throws Exception {
        Product product = new Product("S" + System.currentTimeMillis(), new BigDecimal(System.currentTimeMillis()));
        given(productAPI.findById(product.getId())).willReturn(ResponseEntity.ok(product));

        this.mockMvc.perform(get("/api/v1/products/" + product.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.currentPrice").value(product.getCurrentPrice()));
    }

    @Test
    public void createANewProduct() throws Exception {
        Product product = new Product("S" + System.currentTimeMillis(), new BigDecimal( System.currentTimeMillis()));
        given(productAPI.createANewProduct(product)).willReturn(ResponseEntity.ok(product));

        this.mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(product.getName()))
                .andExpect(jsonPath("currentPrice").value(product.getCurrentPrice()));
    }

    @Test
    public void updatePriceOfAProduct() throws Exception {
        Product product = new Product("S" + System.currentTimeMillis(), new BigDecimal( System.currentTimeMillis()));
        BigDecimal updatingPrice = product.getCurrentPrice().add(BigDecimal.ONE);
        Product updatedProduct = product.clone();
        updatedProduct.setCurrentPrice(updatingPrice);
        given(productAPI.updatePriceOfAProduct(product.getId(), updatingPrice)).willReturn(ResponseEntity.ok(updatedProduct));

        this.mockMvc.perform(put("/api/v1/products/" + product.getId())
                .param("currentPrice", product.getCurrentPrice().add(BigDecimal.ONE).toString())
                .content(new ObjectMapper().writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(product.getName()))
                .andExpect(jsonPath("currentPrice").value(updatedProduct.getCurrentPrice()));
    }

}
