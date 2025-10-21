package com.example.demo;

// ============================================================================
// IMPORTS - Explicação dos Imports Utilizados:
// ============================================================================
// JUnit 5 (Jupiter) - Framework para testes unitários
import org.junit.jupiter.api.BeforeEach;  // Executado antes de cada teste
import org.junit.jupiter.api.Test;        // Marca método como teste executável

// Classes da aplicação - Modelos, controllers e serviços
import com.example.demo.model.Product;           // Modelo de dados
import com.example.demo.controller.ProductController; // Controller a testar  

// Mockito - Framework para criar objetos mock (simulados)
import org.mockito.InjectMocks;     // Injeta mocks automaticamente no objeto testado
import org.mockito.Mock;            // Cria mock de uma dependência
import org.mockito.MockitoAnnotations; // Inicializa anotações do Mockito

// Spring Framework - Para testes de HTTP/REST
import org.springframework.http.ResponseEntity; // Encapsula resposta HTTP com status
import java.util.Optional;  // Para Optional handling nos testes

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;        // Importações estáticas: when, verify, any, etc.
import static org.junit.jupiter.api.Assertions.*; // Importações estáticas: assertEquals, assertTrue, etc.

// ============================================================================
// CLASSE: ProductControllerTest
// Responsabilidade: Testar a camada de Controller (REST endpoints)
// Estratégia: Usar mocks do serviço para isolar o controller de dependências externas
// Padrão: AAA (Arrange-Act-Assert) com verificação de interações via Mockito
// ============================================================================
public class ProductControllerTest {
    
    // ========================================================================
    // MOCKS - Objetos simulados para testes
    // ========================================================================
    
    // @Mock: Cria um objeto mock (simulado) da interface IProductService
    // Este mock permite controlar o comportamento do serviço durante os testes
    // Sem usar @Mock, seria necessário criar a instância real, incluindo suas dependências
    @Mock
    private com.example.demo.service.IProductService productService;

    // @InjectMocks: Cria instância real do ProductController e injeta os @Mock's
    // Mockito automaticamente procura pelos @Mock's e injeta no construtor/setters
    // Isso permite testar o controller com dependências controladas
    @InjectMocks
    private ProductController productController;

    // ========================================================================
    // @BeforeEach: SETUP - Executado antes de CADA teste
    // ========================================================================
    @BeforeEach
    void setUp() {
        // MockitoAnnotations.openMocks(this):
        // - Ativa as anotações @Mock e @InjectMocks nesta classe de teste
        // - Cria instâncias dos mocks (@Mock) e injeta no objeto com @InjectMocks
        // - Sem esta chamada, os mocks não funcionariam
        MockitoAnnotations.openMocks(this);
    }

    // ========================================================================
    // TESTE 1: testGetProductById
    // Objetivo: Validar que o controller recupera um produto por ID corretamente
    // Padrão: ARRANGE -> ACT -> ASSERT
    // ========================================================================
    @Test
    void testGetProductById() {
        // ARRANGE (Preparar):
        // - Criar um novo produto com ID 1L
        // - Configurar o mock do serviço para retornar este produto quando getProductById(1L) for chamado
        Product product = new Product();
        
        Long productId = 1L;
        product.setId(productId);
        // when(...).thenReturn(...): Configura comportamento do mock
        // - Quando productService.getProductById(1L) for chamado
        // - Retorna Optional contendo o produto criado
        when(productService.getProductById(productId)).thenReturn(Optional.of(product));
        
        // ACT (Executar):
        // - Chamar o método do controller que queremos testar
        // - Este método internamente chama productService.getProductById(1L)
        ResponseEntity<Product> response = productController.getProductById(1L);
        
        // ASSERT (Verificar):
        // - Validar que a resposta não é nula (defesa contra NullPointerException)
        assertNotNull(response, "response não deve ser null");
        
        // - Validar o status HTTP: 200 (OK)
        // - ResponseEntity.getStatusCode() retorna HttpStatus
        // - .value() retorna o código numérico (200, 201, 404, etc.)
        assertEquals(200, response.getStatusCode().value());
        
        // - Validar que o corpo da resposta não é nulo
        assertNotNull(response.getBody(), "response.getBody() não deve ser null");
        
        // - Validar que o ID do produto retornado é 1L
        assertEquals(productId, product.getId());
    }

    // ========================================================================
    // TESTE 2: testCreateProduct
    // Objetivo: Validar que o controller cria um novo produto corretamente
    // Padrão: ARRANGE -> ACT -> ASSERT
    // ========================================================================
    @Test
    void testCreateProduct() {
        // ARRANGE (Preparar):
        // - Criar um novo produto com nome "Notebook"
        Product product = new Product();
        product.setName("Notebook");
        
        // - Configurar o mock para retornar o produto quando createProduct for chamado
        // any(Product.class): Matcher do Mockito - aceita QUALQUER objeto Product como argumento
        // - Sem especificar the exact object, torna o teste mais flexível
        when(productService.createProduct(any(Product.class))).thenReturn(product);
        
        // ACT (Executar):
        // - Chamar o método do controller para criar produto
        ResponseEntity<Product> response = productController.createProduct(product);
        
        // ASSERT (Verificar):
        // - Validar que a resposta não é nula
        assertNotNull(response, "response não deve ser null");
        
        // - Validar o status HTTP: 201 (CREATED)
        // - 201 é o status apropriado para criação bem-sucedida de recurso
        assertEquals(201, response.getStatusCode().value());
        
        // - Validar que o corpo da resposta não é nulo
        assertNotNull(response.getBody(), "response.getBody() não deve ser null");
        
        // - Validar que o nome do produto retornado é "Notebook"
        // Após assertNotNull, sabemos que getBody() não é null, portanto é seguro usar
        Product responseBody = response.getBody();
        if (responseBody != null) {
            assertEquals("Notebook", responseBody.getName());
        }
    }    // ========================================================================
    // TESTE 3: testDeleteProduct
    // Objetivo: Validar que o controller deleta um produto corretamente
    // Padrão: ARRANGE -> ACT -> ASSERT
    // ========================================================================
    @Test
    void testDeleteProduct() {
        // ARRANGE (Preparar):
        // - Configurar o mock para retornar true quando deleteProduct(1L) for chamado
        // - true indica que a exclusão foi bem-sucedida
        when(productService.deleteProduct(1L)).thenReturn(true);
        
        // ACT (Executar):
        // - Chamar o método do controller para deletar o produto com ID 1L
        // - ResponseEntity<Void>: Resposta sem conteúdo (corpo vazio)
        ResponseEntity<Void> response = productController.deleteProduct(1L);
        
        // ASSERT (Verificar):
        // - Validar o status HTTP: 204 (NO CONTENT)
        // - 204 é o status apropriado para exclusão bem-sucedida (sem retorno de dados)
        assertEquals(204, response.getStatusCode().value());
        
        // VERIFY (Verificar interação com mock):
        // - Nota: Este teste não faz verify explícito, mas poderia:
        //   verify(productService, times(1)).deleteProduct(1L);
        // - times(1) verifica que o mock foi chamado exatamente 1 vez
    }
}
