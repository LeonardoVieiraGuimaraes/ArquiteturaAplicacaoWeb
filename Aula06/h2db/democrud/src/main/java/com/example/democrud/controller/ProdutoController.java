package com.example.democrud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.democrud.model.Produto;
import com.example.democrud.service.ProdutoService;

@RestController
@RequestMapping("/api/crud")
public class ProdutoController{

    @Autowired
    private ProdutoService produtoService; 
    
    @GetMapping
    public List<Produto> getAll(){
        return produtoService.findAll();
    }

    @PostMapping
    public Produto create(@RequestBody Produto produto){
        return produtoService.save(produto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getById(@PathVariable Long id){
        return produtoService.findByid(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
 
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> update(@PathVariable Long id, @RequestBody Produto updateProduto){
        return produtoService.update(id, updateProduto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

  
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        if(produtoService.deleteById(id)){
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
    

}


  


// // Importações necessárias para o funcionamento do controlador REST.
// import java.util.List; // Para trabalhar com listas de objetos.
// import org.springframework.beans.factory.annotation.Autowired; // Injeta dependências automaticamente.
// import org.springframework.http.ResponseEntity; // Representa respostas HTTP.
// import org.springframework.web.bind.annotation.DeleteMapping; // Define endpoints para requisições DELETE.
// import org.springframework.web.bind.annotation.GetMapping; // Define endpoints para requisições GET.
// import org.springframework.web.bind.annotation.PathVariable; // Captura variáveis de caminho na URL.
// import org.springframework.web.bind.annotation.PostMapping; // Define endpoints para requisições POST.
// import org.springframework.web.bind.annotation.PutMapping; // Define endpoints para requisições PUT.
// import org.springframework.web.bind.annotation.RequestBody; // Captura o corpo da requisição.
// import org.springframework.web.bind.annotation.RequestMapping; // Define o caminho base para os endpoints.
// import org.springframework.web.bind.annotation.RestController; // Marca a classe como um controlador REST.

// import com.example.democrud.model.Produto; // Importa o modelo Produto.
// import com.example.democrud.service.ProdutoService; // Importa o serviço ProdutoService para lógica de negócios.

// // Indica que esta classe é um controlador REST, permitindo que ela manipule requisições HTTP.
// @RestController
// // Define o caminho base "/api/crud" para todos os endpoints desta classe.
// @RequestMapping("/api/crud")
// public class ProdutoController {

//     // Injeta automaticamente uma instância de ProdutoService para uso no controlador.
//     @Autowired
//     private ProdutoService produtoService;

//     // Endpoint para obter todos os produtos.
//     @GetMapping
//     public List<Produto> getAll() {
//         // Chama o serviço para buscar todos os produtos e retorna a lista.
//         return produtoService.findAll();
//     }

//     // Endpoint para obter um produto específico pelo ID.
//     @GetMapping("/{id}")
//     public ResponseEntity<Produto> getById(@PathVariable Long id) {
//         // Busca o produto pelo ID. Se encontrado, retorna 200 OK com o produto.
//         // Caso contrário, retorna 404 Not Found.
//         return produtoService.findById(id)
//                 .map(ResponseEntity::ok) // Retorna 200 OK com o produto encontrado.
//                 .orElse(ResponseEntity.notFound().build()); // Retorna 404 se não encontrado.
//     }

//     // Endpoint para criar um novo produto.
//     @PostMapping
//     public Produto create(@RequestBody Produto produto) {
//         // Recebe o produto no corpo da requisição e o salva no banco de dados.
//         return produtoService.save(produto);
//     }

//     // Endpoint para atualizar um produto existente.
//     @PutMapping("/{id}")
//     public ResponseEntity<Produto> update(@PathVariable Long id, @RequestBody Produto model) {
//         // Atualiza o produto pelo ID. Se encontrado, retorna 200 OK com o produto atualizado.
//         // Caso contrário, retorna 404 Not Found.
//         return produtoService.update(id, model)
//                 .map(ResponseEntity::ok) // Retorna 200 OK com o produto atualizado.
//                 .orElse(ResponseEntity.notFound().build()); // Retorna 404 se não encontrado.
//     }

//     // Endpoint para deletar um produto pelo ID.
//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> delete(@PathVariable Long id) {
//         // Remove o produto pelo ID. Se encontrado e removido, retorna 204 No Content.
//         // Caso contrário, retorna 404 Not Found.
//         if (produtoService.deleteById(id)) {
//             return ResponseEntity.noContent().build(); // Retorna 204 No Content se a exclusão for bem-sucedida.
//         } else {
//             return ResponseEntity.notFound().build(); // Retorna 404 se o produto não for encontrado.
//         }
//     }
// }
