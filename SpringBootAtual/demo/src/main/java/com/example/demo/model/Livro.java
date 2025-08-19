
// Entidade Livro representa um livro na biblioteca
package com.example.demo.model;


import jakarta.persistence.*;


@Entity // Marca a classe como uma entidade JPA
public class Livro {
    @Id // Indica o campo chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera o ID automaticamente
    private Long id;

    private String titulo; // Título do livro
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;  // Autor do livro
    private String isbn;   // ISBN do livro

    // Getters e Setters
    // Métodos para acessar e modificar os atributos
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
}
