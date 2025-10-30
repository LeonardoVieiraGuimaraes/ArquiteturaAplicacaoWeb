package com.example.demo.pedido;

/**
 * Estados de ciclo de vida de um pedido.
 * Mantido simples para o estudo de caso.
 */
public enum StatusPedido {
    /** Pedido foi criado (estado inicial). */
    CRIADO,
    /** Pedido foi processado com sucesso. */
    PROCESSADO,
    /** Houve falha no processamento do pedido. */
    FALHA
}
