package com.seuprojeto.monitoramento;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExemploLogController {

    private static final Logger logger = LoggerFactory.getLogger(ExemploLogController.class);

    @GetMapping("/exemplo-log")
    public String exemploLog() {
        logger.info("Endpoint /exemplo-log foi acessado");
        logger.warn("Este é um log de aviso para demonstração");
        logger.error("Este é um log de erro para demonstração");
        return "Logs gerados! Veja o console da aplicação.";
    }
}
