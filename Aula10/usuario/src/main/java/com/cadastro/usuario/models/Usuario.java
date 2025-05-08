// package com.cadastro.usuario.models;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import java.util.Objects;
// @Entity
// public class Usuario {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;
//     private String nome;
//     private String email;
//     // Construtor sem parâmetros
//     public Usuario() {
//     }
//     // Construtor com todos os campos
//     public Usuario(Long id, String nome, String email) {
//         this.id = id;
//         this.nome = nome;
//         this.email = email;
//     }
//     // Getters e Setters
//     public Long getId() {
//         return id;
//     }
//     public void setId(Long id) {
//         this.id = id;
//     }
//     public String getNome() {
//         return nome;
//     }
//     public void setNome(String nome) {
//         this.nome = nome;
//     }
//     public String getEmail() {
//         return email;
//     }
//     public void setEmail(String email) {
//         this.email = email;
//     }
//     // toString
//     @Override
//     public String toString() {
//         return "Usuario{" +
//                 "id=" + id +
//                 ", nome='" + nome + '\'' +
//                 ", email='" + email + '\'' +
//                 '}';
//     }
//     // equals e hashCode
//     @Override
//     public boolean equals(Object o) {
//         if (this == o) return true;
//         if (o == null || getClass() != o.getClass()) return false;
//         Usuario usuario = (Usuario) o;
//         return Objects.equals(id, usuario.id) &&
//                 Objects.equals(nome, usuario.nome) &&
//                 Objects.equals(email, usuario.email);
//     }
//     @Override
//     public int hashCode() {
//         return Objects.hash(id, nome, email);
//     }
// }
package com.cadastro.usuario.models;

import jakarta.persistence.Entity; // Importa a anotação Entity do JPA para marcar esta classe como uma entidade persistente.
import jakarta.persistence.GeneratedValue; // Importa a anotação GeneratedValue do JPA para especificar a estratégia de geração de valores para o ID.
import jakarta.persistence.GenerationType; // Importa a enumeração GenerationType do JPA para definir as estratégias de geração de valores para o ID.
import jakarta.persistence.Id; // Importa a anotação Id do JPA para marcar o campo como a chave primária da entidade.
import lombok.AllArgsConstructor; // Importa a anotação AllArgsConstructor do Lombok para gerar um construtor com todos os campos como parâmetros.
import lombok.Data;// Importa a anotação @Data do Lombok, que gera automaticamente getters, setters, toString, equals, hashCode e um construtor com todos os campos
import lombok.NoArgsConstructor;// Importa a anotação @NoArgsConstructor do Lombok, que gera automaticamente um construtor sem parâmetros

@Entity // Marca esta classe como uma entidade JPA, que será mapeada para uma tabela no banco de dados.
@Data // Gera automaticamente getters, setters, toString, equals e hashCode usando o Lombok.
@NoArgsConstructor // Gera automaticamente um construtor sem parâmetros usando o Lombok.
@AllArgsConstructor // Gera automaticamente um construtor com todos os campos como parâmetros usando o Lombok.

public class Usuario {

    @Id // Marca este campo como a chave primária da entidade.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Especifica que o valor do ID será gerado automaticamente pelo banco de dados usando a estratégia de identidade.
    private Long id; // Campo que representa o ID do usuário.

    private String nome; // Campo que representa o nome do usuário.
    private String email; // Campo que representa o email do usuário.
}
