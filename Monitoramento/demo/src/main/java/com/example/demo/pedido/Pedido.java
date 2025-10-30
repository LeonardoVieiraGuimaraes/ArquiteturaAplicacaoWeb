package com.example.demo.pedido;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Entidade simples e imutável representando um Pedido.
 * Para fins de demo, usamos apenas campos essenciais e nenhum ORM.
 */
public class Pedido {
    private final UUID id;          // Identificador único do pedido
    private final BigDecimal valor; // Valor monetário do pedido
    private final String usuario;   // Usuário solicitante
    private final Instant criadoEm; // Timestamp de criação
    private final StatusPedido status; // Status do pedido

    public Pedido(UUID id, BigDecimal valor, String usuario, Instant criadoEm, StatusPedido status) {
        this.id = id;
        this.valor = valor;
        this.usuario = usuario;
        this.criadoEm = criadoEm;
        this.status = status;
    }

    public UUID getId() { return id; }
    public BigDecimal getValor() { return valor; }
    public String getUsuario() { return usuario; }
    public Instant getCriadoEm() { return criadoEm; }
    public StatusPedido getStatus() { return status; }
}
