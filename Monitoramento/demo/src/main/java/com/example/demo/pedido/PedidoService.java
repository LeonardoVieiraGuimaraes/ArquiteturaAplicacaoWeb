package com.example.demo.pedido;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PedidoService {
    private static final Logger log = LoggerFactory.getLogger(PedidoService.class);

    private final Map<UUID, Pedido> pedidos = new ConcurrentHashMap<>();

    private final Counter pedidosCriados;
    private final DistributionSummary valorPedidoSummary;
    private final Timer processamentoTimer;

    public PedidoService(MeterRegistry registry) {
        // Contador de pedidos criados (métrica de negócio)
        this.pedidosCriados = Counter.builder("business_pedidos_criados_total")
                .description("Total de pedidos criados")
                .register(registry);

        // Distribuição dos valores dos pedidos com percentis publicados
        this.valorPedidoSummary = DistributionSummary.builder("business_pedidos_valor")
                .description("Distribuição de valores dos pedidos")
                .baseUnit("BRL")
                .publishPercentiles(0.5, 0.9, 0.95, 0.99)
                .register(registry);

        // Tempo de processamento dos pedidos com histograma (útil para Grafana)
        this.processamentoTimer = Timer.builder("business_pedidos_processamento")
                .description("Tempo de processamento dos pedidos")
                .publishPercentileHistogram()
                .register(registry);
    }

    public Pedido criarPedido(BigDecimal valor, String usuario) {
        UUID id = UUID.randomUUID();
        // Adiciona contexto ao log para facilitar correlação em observabilidade
        MDC.put("pedidoId", id.toString());
        MDC.put("usuario", usuario);
        long start = System.nanoTime();
        try {
            // Simula alguma lógica de negócio (ex: validações, cálculo de frete)
            Pedido pedido = new Pedido(id, valor, usuario, Instant.now(), StatusPedido.CRIADO);
            pedidos.put(id, pedido);

            pedidosCriados.increment();
            valorPedidoSummary.record(valor.doubleValue());

            log.info("Pedido criado com sucesso");
            return pedido;
        } catch (Exception e) {
            log.error("Falha ao criar pedido", e);
            throw e;
        } finally {
            long duration = System.nanoTime() - start;
            // Registra o tempo de processamento do pedido
            processamentoTimer.record(duration, java.util.concurrent.TimeUnit.NANOSECONDS);
            MDC.remove("pedidoId");
            MDC.remove("usuario");
        }
    }

    public Optional<Pedido> obter(UUID id) {
        return Optional.ofNullable(pedidos.get(id));
    }

    public List<Pedido> listar() {
        return new ArrayList<>(pedidos.values());
    }
}
