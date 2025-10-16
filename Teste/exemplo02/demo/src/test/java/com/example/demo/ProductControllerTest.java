package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductControllerTest {
    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProductById() {
        Product product = new Product();
        product.setId(1L);
        when(productService.getProductById(1L)).thenReturn(Optional.of(product));
        ResponseEntity<Product> response = productController.getProductById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        product.setName("Notebook");
        when(productService.createProduct(any(Product.class))).thenReturn(product);
        ResponseEntity<Product> response = productController.createProduct(product);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Notebook", response.getBody().getName());
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productService).deleteProduct(1L);
        ResponseEntity<Void> response = productController.deleteProduct(1L);
        assertEquals(204, response.getStatusCodeValue());
    }
}
