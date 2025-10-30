package com.example.demo.pedido.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Payload de criação de pedido recebido pelo endpoint POST /pedidos.
 * Usa Bean Validation para garantir dados mínimos válidos.
 */
public record NovoPedidoRequest(
        @NotNull @DecimalMin(value = "0.01", inclusive = true) BigDecimal valor,
        @NotBlank String usuario
) {}
