package com.example.demo.controller; // Define o pacote onde esta classe está localizada

import java.util.ArrayList; // Importa a classe ArrayList
import java.util.List; // Importa a interface List

import org.springframework.http.HttpStatus; // Importa a enumeração HttpStatus
import org.springframework.web.bind.annotation.*; // Importa todas as anotações do Spring Web

import com.example.demo.model.Product; // Importa a classe Product do pacote model

@RestController // Indica que esta classe é um controlador REST
@RequestMapping("/products") // Define o mapeamento base para todas as requisições deste controlador
public class ProductController {

    // Lista de produtos armazenados em memória
    private final List<Product> products = new ArrayList<>();
    // Variável para gerar IDs únicos para novos produtos
    private long nextId = 1L;

    // GET: Listar todos os produtos
    @GetMapping
    public List<Product> getAllProducts() {
        return products; // Retorna a lista de produtos
    }

    // POST: Adicionar um novo produto
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Define o status HTTP 201 Created para a resposta
    public Product addProduct(@RequestBody Product product) {
        product.setId(nextId++); // Define um ID único para o novo produto
        products.add(product); // Adiciona o produto à lista
        return product; // Retorna o produto adicionado
    }

    // GET: Buscar produto por ID
    @GetMapping("/{id}")
    public Object getProductById(@PathVariable Long id) {
        for (Product product : products) { // Itera sobre a lista de produtos
            if (product.getId().equals(id)) { // Verifica se o ID do produto corresponde ao ID fornecido
                return product; // Retorna o produto encontrado
            }
        }
        return "Produto não encontrado"; // Retorna uma mensagem se o produto não for encontrado
    }

    // PUT: Atualizar um produto existente
    @PutMapping("/{id}")
    public Object updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        for (Product product : products) { // Itera sobre a lista de produtos
            if (product.getId().equals(id)) { // Verifica se o ID do produto corresponde ao ID fornecido
                product.setName(updatedProduct.getName()); // Atualiza o nome do produto
                return product; // Retorna o produto atualizado
            }
        }
        return "Produto não encontrado"; // Retorna uma mensagem se o produto não for encontrado
    }

    // DELETE: Remover um produto por ID
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        for (Product product : products) { // Itera sobre a lista de produtos
            if (product.getId().equals(id)) { // Verifica se o ID do produto corresponde ao ID fornecido
                products.remove(product); // Remove o produto da lista
                return "Produto removido com sucesso"; // Retorna uma mensagem de sucesso
            }
        }
        return "Produto não encontrado"; // Retorna uma mensagem se o produto não for encontrado
    }
}