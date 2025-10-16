package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.*;

public class ProductTest {
    private ProductService service;
    private ProductRepository repository;

    @BeforeEach
    void setup() {
        repository = mock(ProductRepository.class);
        service = new ProductService(repository);
    }

    @Test
    void testProductGettersAndSetters() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Notebook");
        product.setDescription("Notebook Dell");
        product.setPrice(3500.0);
        product.setStock(10);
        assertEquals(1L, product.getId());
        assertEquals("Notebook", product.getName());
        assertEquals("Notebook Dell", product.getDescription());
        assertEquals(3500.0, product.getPrice());
        assertEquals(10, product.getStock());
    }

    @Test
    void testProductServiceWithMock() {
        // Supondo que exista uma interface ProductService
        ProductService service = mock(ProductService.class);
        Product product = new Product();
        product.setId(1L);
        product.setName("Notebook");
        when(service.save(any(Product.class))).thenReturn(product);

        Product result = service.save(new Product());
        assertEquals(1L, result.getId());
        assertEquals("Notebook", result.getName());
        verify(service, times(1)).save(any(Product.class));
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
}
