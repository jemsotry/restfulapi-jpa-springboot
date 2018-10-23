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
    	Product Product = new Product("Nintendo Switch", new BigDecimal(255), "Videogame Console");
    	List<Product> allProducts = Arrays.asList(Product);
    	given(productAPI.findAll()).willReturn(ResponseEntity.ok(allProducts));
        this.mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    public void findById() throws Exception {
		Product product = new Product("S" + System.currentTimeMillis(), new BigDecimal(1000),
				"Nintendo Switch");
        given(productAPI.findById(product.getId())).willReturn(ResponseEntity.ok(product));

        this.mockMvc.perform(get("/api/v1/products/" + product.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.currentPrice").value(product.getCurrentPrice()))
                .andExpect(jsonPath("$.description").value(product.getDescription()));
    }

    @Test
    public void createANewProduct() throws Exception {
        Product product = new Product("S" + System.currentTimeMillis(), new BigDecimal( System.currentTimeMillis()), "Nintendo Switch");
        given(productAPI.createANewProduct(product)).willReturn(ResponseEntity.ok(product));

        this.mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(product.getName()))
                .andExpect(jsonPath("currentPrice").value(product.getCurrentPrice()));
                //.andExpect(jsonPath("description").value(product.getDescription()));
    }

    @Test
    public void updatePriceOfAProduct() throws Exception {
		Product product = new Product("S" + System.currentTimeMillis(), new BigDecimal(System.currentTimeMillis()),
				"Nintendo Switch");
        BigDecimal updatingPrice = product.getCurrentPrice().add(BigDecimal.ONE);
        String updatingName = product.getName().concat("_2018");
        String updatingDescription = product.getDescription();
        Product updatedProduct = product.clone();
        updatedProduct.setCurrentPrice(updatingPrice);
        updatedProduct.setName(updatingName);
        updatedProduct.setDescription(updatingDescription);

		given(productAPI.updatePriceOfAProduct(product.getId(), updatingName, updatingPrice, updatingDescription))
				.willReturn(ResponseEntity.ok(updatedProduct));

        this.mockMvc.perform(put("/api/v1/products/" + product.getId())
                .param("name", product.getName().concat("_2018"))
                .param("currentPrice", product.getCurrentPrice().add(BigDecimal.ONE).toString())
                .param("description", product.getDescription())
                .content(new ObjectMapper().writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(updatedProduct.getName()))
                .andExpect(jsonPath("currentPrice").value(updatedProduct.getCurrentPrice()))
                .andExpect(jsonPath("description").value(product.getDescription()));
    }

}
