package com.example.demo.pedido;

import com.example.demo.pedido.dto.NovoPedidoRequest;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pedidos")
@Validated
public class PedidoController {

    private static final Logger log = LoggerFactory.getLogger(PedidoController.class);
    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Timed(value = "http_pedidos_criar", description = "Tempo do POST /pedidos", histogram = true)
    public Pedido criar(@Valid @RequestBody NovoPedidoRequest req) {
        // Inclui o usuário no MDC para enriquecer os logs
        MDC.put("usuario", req.usuario());
        try {
            log.debug("Recebida requisição de criação de pedido");
            return service.criarPedido(req.valor(), req.usuario());
        } finally {
            MDC.remove("usuario");
        }
    }

    @GetMapping
    @Timed(value = "http_pedidos_listar", description = "Tempo do GET /pedidos", histogram = true)
    public List<Pedido> listar() {
        log.debug("Listando pedidos");
        return service.listar();
    }

    @GetMapping("/{id}")
    @Timed(value = "http_pedidos_obter", description = "Tempo do GET /pedidos/{id}", histogram = true)
    public ResponseEntity<Pedido> obter(@PathVariable UUID id) {
        MDC.put("pedidoId", id.toString());
        try {
            log.debug("Buscando pedido por id");
            return service.obter(id)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } finally {
            MDC.remove("pedidoId");
        }
    }
}
