
// DTO (Data Transfer Object) para transferir dados do Livro entre camadas
package com.example.demo.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LivroDTO {
    private Long id;         // Identificador do livro

    @NotBlank(message = "O título é obrigatório")
    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres")
    private String titulo;   // Título do livro

    @NotBlank(message = "O autor é obrigatório")
    @Size(max = 60, message = "O autor deve ter no máximo 60 caracteres")
    private String autor;    // Autor do livro

    @NotBlank(message = "O ISBN é obrigatório")
    @Size(max = 20, message = "O ISBN deve ter no máximo 20 caracteres")
    private String isbn;     // ISBN do livro

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
