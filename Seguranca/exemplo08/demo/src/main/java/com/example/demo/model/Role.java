package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Exemplo: ROLE_USER, ROLE_ADMIN

    @Column(nullable = false)
    private String displayName; // Exemplo: Usuário, Administrador

    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    private User createdBy; // Usuário responsável pelo cadastro da role
}
