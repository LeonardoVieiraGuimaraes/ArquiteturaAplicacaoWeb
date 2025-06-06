package com.example.demo.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade User representa um usuário do sistema. Inclui validações e
 * restrições de unicidade para username, CPF e e-mail.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único

    @NotBlank(message = "Username é obrigatório")
    @Size(min = 3, max = 50, message = "Username deve ter entre 3 e 50 caracteres")
    @Column(nullable = false, unique = true)
    private String username; // Nome de login único

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
    @Column(nullable = false)
    private String password; // Senha do usuário

    @Column(nullable = false)
    private boolean enabled; // Indica se o usuário está ativo

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles; // Perfis do usuário (ex: ROLE_USER, ROLE_ADMIN)

    @NotBlank(message = "Nome completo é obrigatório")
    @Size(min = 3, max = 100, message = "Nome completo deve ter entre 3 e 100 caracteres")
    @Column(nullable = false)
    private String nomeCompleto; // Nome completo do usuário

    @NotBlank(message = "CPF é obrigatório")
    @Size(min = 11, max = 14, message = "CPF deve ter entre 11 e 14 caracteres")
    @Column(nullable = false, unique = true)
    private String cpf; // CPF único

    @NotBlank(message = "E-mail é obrigatório")
    @Size(max = 100, message = "E-mail deve ter no máximo 100 caracteres")
    @Column(nullable = false, unique = true)
    private String email; // E-mail único

    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    private User createdBy; // Usuário responsável pelo cadastro
}
