package com.example.demo;

// ========== IMPORTS: JUNIT 5 ==========
// @Test: anotação que marca um método como teste
// @BeforeEach: anotação que executa um método antes de cada teste
// Assertions: assertEquals, assertTrue, assertNotNull, etc. - para verificar resultados
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

// ========== IMPORTS: CLASSES DO PROJETO ==========
// Product: modelo/entidade de dados
// ProductRepository: interface do repositório JPA (acesso ao banco de dados)
// ProductService: serviço com lógica de negócio (será testado)
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;

// ========== IMPORTS: MOCKITO ==========
// any(): matcher que aceita qualquer argumento
// mock, when, verify, times, doNothing: funções para criar e configurar mocks (simulações)
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// ========== IMPORTS: JAVA UTIL ==========
// Optional: tipo que pode conter um valor ou estar vazio
import java.util.*;

// ========== CLASSE DE TESTE ==========
// ProductTest: contém testes unitários para ProductService
// Um teste unitário verifica uma pequena unidade de código isoladamente
public class ProductTest {
    // Campo: ProductService - é o serviço a ser testado (System Under Test)
    // Será instanciado no setup() com um repositório mock
    private ProductService service;
    
    // Campo: ProductRepository - será mockado para evitar acesso real ao banco de dados
    // Mockamos para ter total controle sobre os retornos
    private ProductRepository repository;
    
    // ========== MÉTODO DE CONFIGURAÇÃO ==========
    // @BeforeEach: executa ANTES de cada teste para preparar o estado inicial
    // Assim cada teste começa limpo e controlado
    @BeforeEach
    void setup() {
        // Cria um mock (simulação) do ProductRepository
        // Não conecta a banco de dados real; controlamos o que ele retorna
        repository = mock(ProductRepository.class);
        
        // Cria ProductService INJETANDO o mock do repositório
        // Isso é "injeção de dependência": o serviço usa o mock durante o teste
        service = new ProductService(repository);
    }


    // ========== TESTE 1: GETTERS E SETTERS DO MODELO ==========
    // Propósito: verificar que os métodos get/set do modelo Product funcionam corretamente
    // Padrão: ARRANGE (preparar) -> ACT (agir) -> ASSERT (verificar)
    @Test
    void testProductGettersAndSetters() {
        // ARRANGE: Cria um produto vazio
        Product product = new Product();
        
        // ARRANGE: Configura os valores via SETTERS
        product.setId(1L);           // Define ID = 1
        product.setName("Notebook"); // Define nome
        product.setDescription("Notebook Dell"); // Define descrição
        product.setPrice(3500.0);    // Define preço
        product.setStock(10);        // Define estoque
        
        // ACT + ASSERT: Verifica que os GETTERS retornam os mesmos valores
        // assertEquals(valor_esperado, valor_obtido)
        assertEquals(1L, product.getId());
        assertEquals("Notebook", product.getName());
        assertEquals("Notebook Dell", product.getDescription());
        assertEquals(3500.0, product.getPrice());
        assertEquals(10, product.getStock());
        // Se qualquer assertion falhar, o teste para aqui e mostra qual falhou
    }


    // ========== TESTE 2: SERVIÇO COM MOCK ==========
    // Propósito: demonstrar como mockar a INTERFACE do serviço (IProductService)
    // Este é um teste educacional que mostra o uso de mocks do Mockito
    // Útil para testes de controlador que dependem do serviço
    @Test
    void testProductServiceWithMock() {
        // ARRANGE: Mocka a interface IProductService (não é a classe real)
        // Quando chamarmos métodos, vamos retornar o que configurarmos com when()
        com.example.demo.service.IProductService service = mock(com.example.demo.service.IProductService.class);
        
        // ARRANGE: Prepara dados de teste
        Product product = new Product();
        product.setId(1L);
        product.setName("Notebook");
        
        // ARRANGE: Configura o mock
        // "quando addProduct for chamado com ANY Product, retorna nosso product"
        // any(Product.class): aceita QUALQUER produto como argumento
        when(service.addProduct(any(Product.class))).thenReturn(product);

        // ACT: Executa o método do mock (passando um Product vazio)
        Product result = service.addProduct(new Product());
        
        // ASSERT: Verifica que o resultado tem os dados esperados
        assertEquals(1L, result.getId());
        assertEquals("Notebook", result.getName());
        
        // VERIFY: Verifica que o método foi chamado EXATAMENTE 1 vez
        // verify: valida que o mock foi usado como esperado
        // times(1): exatamente 1 vez
        verify(service, times(1)).addProduct(any(Product.class));
    }


    // ========== TESTE 3: CRIAR PRODUTO ==========
    // Propósito: testar o método createProduct() do ProductService (implementação real com mock do repository)
    // Cenário: criar um novo produto no banco de dados
    @Test
    void testCreateProduct() {
        // ARRANGE: Prepara dados de teste
        Product product = new Product();
        product.setId(1L);
        product.setName("Notebook");
        
        // ARRANGE: Configura o mock do repositório
        // "quando repository.save for chamado com ANY Product, retorna nosso product"
        // Isso simula que o banco salvou e retornou o objeto
        when(repository.save(any(Product.class))).thenReturn(product);
        
        // ACT: Executa o método createProduct do SERVIÇO REAL (que usa o mock)
        Product saved = service.createProduct(product);
        
        // ASSERT: Verifica o resultado
        assertEquals("Notebook", saved.getName());
        
        // VERIFY: Verifica que o repositório foi usado
        // Garante que service.createProduct de fato chamou repository.save
        verify(repository, times(1)).save(product);
    }

    // ========== TESTE 4: LER PRODUTO ==========
    // Propósito: testar o método getProductById() do ProductService
    // Cenário: buscar um produto no banco pelo seu ID
    @Test
    void testReadProduct() {
        // ARRANGE: Prepara dados de teste
        Product product = new Product();
        product.setId(1L);
        
        // ARRANGE: Configura o mock
        // "quando repository.findById for chamado com 1L, retorna um Optional com o produto"
        // Optional: tipo que pode conter um valor (Optional.of) ou estar vazio (Optional.empty)
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        
        // ACT: Executa o método getProductById do SERVIÇO
        Optional<Product> found = service.getProductById(1L);
        
        // ASSERT: Verifica que o Optional contém um valor (não está vazio)
        // assertTrue: verifica que a condição é verdadeira
        assertTrue(found.isPresent());
        
        // ASSERT: Extrai o valor do Optional e verifica o ID
        // found.get(): extrai o Product de dentro do Optional
        assertEquals(1L, found.get().getId());
        
        // VERIFY: Verifica que o repositório foi usado
        verify(repository, times(1)).findById(1L);
    }

    // ========== TESTE 5: ATUALIZAR PRODUTO ==========
    // Propósito: testar o método updateProduct() do ProductService
    // Cenário: atualizar um produto no banco de dados
    @Test
    void testUpdateProduct() {
        // ARRANGE: Prepara dados de teste
        Product product = new Product();
        product.setId(1L);
        product.setName("Notebook");
        
        // ARRANGE: Configura o mock
        // "quando repository.save for chamado com ANY Product, retorna nosso product"
        when(repository.save(any(Product.class))).thenReturn(product);
        
        // ACT: Executa o método updateProduct do SERVIÇO
        Product updated = service.updateProduct(product);
        
        // ASSERT: Verifica que o produto foi atualizado
        assertEquals("Notebook", updated.getName());
        
        // VERIFY: Verifica que o repositório foi usado
        verify(repository, times(1)).save(product);
    }

    // ========== TESTE 6: DELETAR PRODUTO ==========
    // Propósito: testar o método deleteProduct() do ProductService
    // Cenário: remover um produto do banco de dados pelo seu ID
    @Test
    void testDeleteProduct() {
        // ARRANGE: Configura o mock - PARTE 1
        // "quando repository.existsById for chamado com 1L, retorna true"
        // O serviço verifica se o produto existe antes de deletar
        when(repository.existsById(1L)).thenReturn(true);
        
        // ARRANGE: Configura o mock - PARTE 2
        // "quando repository.deleteById for chamado com 1L, não faça nada"
        // doNothing(): método void que não lança exceção
        doNothing().when(repository).deleteById(1L);
        
        // ACT: Executa o método deleteProduct do SERVIÇO
        // Não há retorno (void), então não armazenamos resultado
        service.deleteProduct(1L);
        
        // ASSERT: Neste caso não há valor de retorno para verificar
        
        // VERIFY: Verifica que o método de delete foi chamado
        // Garante que service.deleteProduct de fato chamou repository.deleteById
        verify(repository, times(1)).deleteById(1L);
    }
}
