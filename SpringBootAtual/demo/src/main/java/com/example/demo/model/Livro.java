
// Entidade Livro representa um livro na biblioteca
package com.example.demo.model;


import jakarta.persistence.Entity; // Indica que esta classe é uma entidade JPA
import jakarta.persistence.GeneratedValue; // Geração automática do ID
import jakarta.persistence.GenerationType; // Estratégia de geração do ID
import jakarta.persistence.Id; // Indica o campo chave primária


@Entity // Marca a classe como uma entidade JPA
public class Livro {
    @Id // Indica o campo chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera o ID automaticamente
    private Long id;

    private String titulo; // Título do livro
    private String autor;  // Autor do livro
    private String isbn;   // ISBN do livro

    // Getters e Setters
    // Métodos para acessar e modificar os atributos
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
}
