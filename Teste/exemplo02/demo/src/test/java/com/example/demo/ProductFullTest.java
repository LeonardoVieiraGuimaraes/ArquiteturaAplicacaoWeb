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

public class ProductFullTest {
    // --- Testes de Serviço ---
    private ProductService service;
    private ProductRepository repository;

    @BeforeEach
    void setupService() {
        repository = mock(ProductRepository.class);
        service = new ProductService(repository);
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Notebook");
        when(repository.save(any(Product.class))).thenReturn(product);
        Product saved = service.createProduct(product);
        assertEquals("Notebook", saved.getName());
        verify(repository, times(1)).save(product);
    }

    @Test
    void testReadProduct() {
        Product product = new Product();
        product.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        Optional<Product> found = service.getProductById(1L);
        assertTrue(found.isPresent());
        assertEquals(1L, found.get().getId());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testUpdateProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Notebook");
        when(repository.save(any(Product.class))).thenReturn(product);
        Product updated = service.updateProduct(product);
        assertEquals("Notebook", updated.getName());
        verify(repository, times(1)).save(product);
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(repository).deleteById(1L);
        service.deleteProduct(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    // --- Testes de Controlador ---
    @Mock
    private ProductService productService;
    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setupController() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProductByIdController() {
        Product product = new Product();
        product.setId(1L);
        when(productService.getProductById(1L)).thenReturn(Optional.of(product));
        ResponseEntity<Product> response = productController.getProductById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testCreateProductController() {
        Product product = new Product();
        product.setName("Notebook");
        when(productService.createProduct(any(Product.class))).thenReturn(product);
        ResponseEntity<Product> response = productController.createProduct(product);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Notebook", response.getBody().getName());
    }

    @Test
    void testDeleteProductController() {
        doNothing().when(productService).deleteProduct(1L);
        ResponseEntity<Void> response = productController.deleteProduct(1L);
        assertEquals(204, response.getStatusCodeValue());
    }
}
