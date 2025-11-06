package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * DTO de entrada para criação/atualização de Autor.
 * - Contém apenas os campos que o cliente pode enviar.
 * - Validação via Jakarta Validation para garantir qualidade dos dados.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorRequest {

    @NotBlank(message = "Nome do autor é obrigatório")
    @Size(max = 120, message = "Nome do autor deve ter no máximo 120 caracteres")
    private String name;
}
