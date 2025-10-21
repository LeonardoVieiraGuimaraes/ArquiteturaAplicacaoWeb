package com.example.demo;

// ============================================================================
// IMPORTS - Explicação dos Imports Utilizados:
// ============================================================================
// JUnit 5 (Jupiter)
import org.junit.jupiter.api.BeforeEach;  // Executado antes de cada teste
import org.junit.jupiter.api.Test;        // Marca método como teste executável

// Mockito - Framework para mocks
import org.mockito.InjectMocks;     // Injeta mocks automaticamente
import org.mockito.Mock;            // Cria mock de dependência
import org.mockito.MockitoAnnotations; // Inicializa anotações do Mockito

// Spring Framework
import org.springframework.http.ResponseEntity; // Resposta HTTP

// Classes da aplicação
import com.example.demo.controller.ProductController; // Controller a testar
import com.example.demo.model.Product;                // Modelo de dados
import com.example.demo.repository.ProductRepository; // Repositório (acesso a dados)
import com.example.demo.service.ProductService;       // Serviço (regra de negócio)

// Utilitários
import java.util.*;
import static org.mockito.Mockito.*;        // when, verify, any, doNothing, times, etc.
import static org.junit.jupiter.api.Assertions.*; // assertEquals, assertTrue, assertNotNull, etc.

// ============================================================================
// CLASSE: ProductFullTest
// Responsabilidade: Testes INTEGRADOS que cobrem SERVIÇO e CONTROLLER
// Estratégia: Testar a aplicação em dois níveis:
//   1) Nível de SERVIÇO: com repositório mockado
//   2) Nível de CONTROLLER: com serviço mockado
// Padrão: AAA (Arrange-Act-Assert) com Mockito para mock/verify
// ============================================================================
public class ProductFullTest {
    
    // ========================================================================
    // SEÇÃO 1: TESTES DO SERVIÇO (ProductService)
    // Descrição: Testa a lógica de negócio com repositório simulado
    // ========================================================================
    
    // Campos para testes de serviço (NÃO usam @Mock ou @InjectMocks)
    // Instâncias reais são criadas manualmente no @BeforeEach
    private ProductService service;                    // Serviço real para testar
    private ProductRepository repository;              // Repository mockado manualmente

    // ========================================================================
    // SETUP DO SERVIÇO: setupService()
    // Executado ANTES de CADA teste de serviço
    // ========================================================================
    @BeforeEach
    void setupService() {
        // mock(ProductRepository.class):
        // - Cria um mock manual da interface ProductRepository
        // - NÃO usa @Mock annotation, cria manualmente com mock()
        // - Mais flexibilidade para testes que usam múltiplos setup's
        repository = mock(ProductRepository.class);
        
        // new ProductService(repository):
        // - Cria instância REAL do serviço
        // - Injeta o repository mockado via construtor
        // - Quando o serviço chamar repository.save(), obterá resposta do mock
        service = new ProductService(repository);
    }

    // ========================================================================
    // TESTE DE SERVIÇO 1: testCreateProduct
    // Objetivo: Validar que o serviço cria produto corretamente
    // Padrão: ARRANGE -> ACT -> ASSERT -> VERIFY
    // ========================================================================
    @Test
    void testCreateProduct() {
        // ARRANGE: Preparar dados e mock
        Product product = new Product();
        product.setId(1L);
        product.setName("Notebook");
        
        // Configurar mock: quando repository.save(any(Product.class)) for chamado
        // o mock retorna o produto que foi passado como argumento
        when(repository.save(any(Product.class))).thenReturn(product);
        
        // ACT: Executar o método a testar
        // O serviço chama repository.save() que retorna conforme configurado acima
        Product saved = service.createProduct(product);
        
        // ASSERT: Verificar resultado
        // Validar que o produto retornado tem o nome esperado
        assertEquals("Notebook", saved.getName());
        
        // VERIFY: Verificar interação com o mock
        // Validar que repository.save() foi chamado EXATAMENTE 1 vez com o produto
        verify(repository, times(1)).save(product);
    }

    // ========================================================================
    // TESTE DE SERVIÇO 2: testReadProduct
    // Objetivo: Validar que o serviço recupera produto por ID corretamente
    // Padrão: ARRANGE -> ACT -> ASSERT -> VERIFY
    // ========================================================================
    @Test
    void testReadProduct() {
        // ARRANGE: Preparar dados e mock
        Product product = new Product();
        product.setId(1L);
        
        // Configurar mock: quando repository.findById(1L) for chamado
        // retorna Optional contendo o produto
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        
        // ACT: Executar o método a testar
        // O serviço chama repository.findById() que retorna conforme mock
        Optional<Product> found = service.getProductById(1L);
        
        // ASSERT: Verificar resultado
        // Validar que Optional está presente (tem um valor dentro)
        assertTrue(found.isPresent());
        
        // Validar que o ID do produto recuperado é 1L
        assertEquals(1L, found.get().getId());
        
        // VERIFY: Verificar interação com o mock
        // Validar que repository.findById(1L) foi chamado EXATAMENTE 1 vez
        verify(repository, times(1)).findById(1L);
    }

    // ========================================================================
    // TESTE DE SERVIÇO 3: testUpdateProduct
    // Objetivo: Validar que o serviço atualiza produto corretamente
    // Padrão: ARRANGE -> ACT -> ASSERT -> VERIFY
    // ========================================================================
    @Test
    void testUpdateProduct() {
        // ARRANGE: Preparar dados e mock
        Product product = new Product();
        product.setId(1L);
        product.setName("Notebook");
        
        // Configurar mock: quando repository.save() for chamado com qualquer Product
        // retorna o produto passado (que já foi atualizado pelo serviço)
        when(repository.save(any(Product.class))).thenReturn(product);
        
        // ACT: Executar o método a testar
        // O serviço chama repository.save() que retorna conforme mock
        Product updated = service.updateProduct(product);
        
        // ASSERT: Verificar resultado
        // Validar que o produto retornado tem o nome esperado
        assertEquals("Notebook", updated.getName());
        
        // VERIFY: Verificar interação com o mock
        // Validar que repository.save() foi chamado EXATAMENTE 1 vez com o produto
        verify(repository, times(1)).save(product);
    }

    // ========================================================================
    // TESTE DE SERVIÇO 4: testDeleteProduct
    // Objetivo: Validar que o serviço deleta produto corretamente
    // Padrão: ARRANGE -> ACT -> ASSERT -> VERIFY
    // ========================================================================
    @Test
    void testDeleteProduct() {
        // ARRANGE: Preparar dados e mock
        // Configurar mock: quando repository.existsById(1L) for chamado
        // retorna true (produto existe)
        when(repository.existsById(1L)).thenReturn(true);
        
        // doNothing(): Configura o mock para não fazer nada (void method)
        // Quando repository.deleteById(1L) for chamado, apenas registra a chamada
        doNothing().when(repository).deleteById(1L);
        
        // ACT: Executar o método a testar
        // O serviço chama repository.deleteById(1L)
        service.deleteProduct(1L);
        
        // VERIFY: Verificar interação com o mock
        // Validar que repository.deleteById(1L) foi chamado EXATAMENTE 1 vez
        verify(repository, times(1)).deleteById(1L);
    }

    // ========================================================================
    // SEÇÃO 2: TESTES DO CONTROLLER (ProductController)
    // Descrição: Testa a camada REST com serviço simulado
    // Nota: Usa @Mock e @InjectMocks (anotações do Mockito)
    // ========================================================================
    
    // @Mock: Cria mock da interface IProductService
    // Este mock será injetado no controller via @InjectMocks
    @Mock
    private com.example.demo.service.IProductService productService;
    
    // @InjectMocks: Cria instância real do ProductController
    // Mockito automaticamente injeta o @Mock productService no construtor/setter
    @InjectMocks
    private ProductController productController;

    // ========================================================================
    // SETUP DO CONTROLLER: setupController()
    // Executado ANTES de CADA teste de controller
    // ========================================================================
    @BeforeEach
    void setupController() {
        // MockitoAnnotations.openMocks(this):
        // - Inicializa @Mock e @InjectMocks nesta classe
        // - Cria mocks e injeta no objeto com @InjectMocks
        // - NOTA: Este método é separado de setupService()
        //         Cada @BeforeEach é executado antes de cada teste,
        //         logo ambos setupService() e setupController() são chamados!
        MockitoAnnotations.openMocks(this);
    }

    // ========================================================================
    // TESTE DE CONTROLLER 1: testGetProductByIdController
    // Objetivo: Validar que o controller recupera produto por ID
    // Padrão: ARRANGE -> ACT -> ASSERT
    // ========================================================================
    @Test
    void testGetProductByIdController() {
        // ARRANGE: Preparar dados e mock
        Product product = new Product();
        product.setId(1L);
        
        // Configurar mock do serviço: quando getProductById(1L) for chamado
        // retorna Optional contendo o produto
        when(productService.getProductById(1L)).thenReturn(Optional.of(product));
        
        // ACT: Executar o método do controller
        // O controller chama productService.getProductById(1L)
        ResponseEntity<Product> response = productController.getProductById(1L);
        
        // ASSERT: Verificar resultado
        // Validar que a resposta não é nula
        assertNotNull(response, "response não deve ser null");
        
        // Validar status HTTP: 200 (OK)
        assertEquals(200, response.getStatusCode().value());
        
        // Validar que o corpo da resposta não é nulo
        assertNotNull(response.getBody(), "response.getBody() não deve ser null");
        
        // Validar que o ID do produto retornado é 1L
        // Após assertNotNull, sabemos que getBody() não é null, portanto é seguro usar
        Product responseBody = response.getBody();
        if (responseBody != null) {
            assertEquals(1L, responseBody.getId());
        }
    }

    // ========================================================================
    // TESTE DE CONTROLLER 2: testCreateProductController
    // Objetivo: Validar que o controller cria novo produto
    // Padrão: ARRANGE -> ACT -> ASSERT
    // ========================================================================
    @Test
    void testCreateProductController() {
        // ARRANGE: Preparar dados e mock
        Product product = new Product();
        product.setName("Notebook");
        
        // Configurar mock do serviço: quando createProduct(any(Product.class)) for chamado
        // retorna o produto (simulando criação bem-sucedida)
        when(productService.createProduct(any(Product.class))).thenReturn(product);
        
        // ACT: Executar o método do controller
        // O controller chama productService.createProduct(product)
        ResponseEntity<Product> response = productController.createProduct(product);
        
        // ASSERT: Verificar resultado
        // Validar que a resposta não é nula
        assertNotNull(response, "response não deve ser null");
        
        // Validar status HTTP: 201 (CREATED)
        assertEquals(201, response.getStatusCode().value());
        
        // Validar que o corpo da resposta não é nulo
        assertNotNull(response.getBody(), "response.getBody() não deve ser null");
        
        // Validar que o nome do produto retornado é "Notebook"
        // Após assertNotNull, sabemos que getBody() não é null, portanto é seguro usar
        Product responseBody = response.getBody();
        if (responseBody != null) {
            assertEquals("Notebook", responseBody.getName());
        }
    }

    // ========================================================================
    // TESTE DE CONTROLLER 3: testDeleteProductController
    // Objetivo: Validar que o controller deleta produto
    // Padrão: ARRANGE -> ACT -> ASSERT
    // ========================================================================
    @Test
    void testDeleteProductController() {
        // ARRANGE: Preparar dados e mock
        // Configurar mock do serviço: quando deleteProduct(1L) for chamado
        // retorna true (exclusão bem-sucedida)
        when(productService.deleteProduct(1L)).thenReturn(true);
        
        // ACT: Executar o método do controller
        // O controller chama productService.deleteProduct(1L)
        ResponseEntity<Void> response = productController.deleteProduct(1L);
        
        // ASSERT: Verificar resultado
        // Validar status HTTP: 204 (NO CONTENT)
        // 204 é apropriado para DELETE bem-sucedido (sem retorno de dados)
        assertEquals(204, response.getStatusCode().value());
    }
}
