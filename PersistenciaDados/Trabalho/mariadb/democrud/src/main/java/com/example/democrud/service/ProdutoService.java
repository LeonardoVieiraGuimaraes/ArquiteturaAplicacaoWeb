package com.example.democrud.service; 
// Define o pacote onde esta classe está localizada.

import java.util.List;
import java.util.Optional;
// Importa classes utilitárias para trabalhar com listas e valores opcionais.

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// Importa anotações do Spring para injeção de dependência e definição de serviços.

import com.example.democrud.model.Produto;
import com.example.democrud.repository.ProdutoRepository;
// Importa a classe Produto e o repositório ProdutoRepository para operações no banco de dados.

@Service 
// Marca a classe como um serviço do Spring, permitindo que ela seja injetada em outras partes do código.
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository; 
    // Injeta automaticamente o repositório ProdutoRepository para realizar operações no banco de dados.

    public List<Produto> findAll() {
        // Método público que retorna todos os registros do banco de dados.
        return produtoRepository.findAll(); 
        // Chama o método findAll() do repositório para buscar todos os produtos.
    }

    public Optional<Produto> findById(Long id) {
        // Método público que busca um registro pelo ID.
        return produtoRepository.findById(id); 
        // Chama o método findById() do repositório para buscar o produto correspondente ao ID.
    }

    public Produto save(Produto model) {
        // Método público que salva um novo registro no banco de dados.
        return produtoRepository.save(model); 
        // Chama o método save() do repositório para persistir o produto.
    }

    public boolean deleteById(Long id) {
        // Método público que remove um registro pelo ID, se ele existir.
        if (produtoRepository.existsById(id)) { 
            // Verifica se o registro existe no banco de dados.
            produtoRepository.deleteById(id); 
            // Remove o registro correspondente ao ID.
            return true; 
            // Retorna true indicando que a exclusão foi bem-sucedida.
        }
        return false; 
        // Retorna false se o registro não existir.
    }

    public Optional<Produto> update(Long id, Produto updatedModel) {
        // Método público que atualiza um registro existente pelo ID.
        return produtoRepository.findById(id).map(existingModel -> { 
            // Busca o registro pelo ID e, se encontrado, executa a atualização.
            existingModel.setName(updatedModel.getName()); 
            // Atualiza o campo "name" do registro existente.
            existingModel.setDescription(updatedModel.getDescription()); 
            // Atualiza o campo "description" do registro existente.
            return produtoRepository.save(existingModel); 
            // Salva as alterações no banco de dados e retorna o registro atualizado.
        });
    }
}
