package br.com.andre.testchain.service;

import br.com.andre.testchain.chain.Handler;
import br.com.andre.testchain.context.PokemonContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PokemonPipelineService {

    private final List<Handler> handlers;

    public PokemonPipelineService(List<Handler> handlers) {
        this.handlers = handlers;
    }

    public PokemonContext process(PokemonContext ctx) {
        log.info("Iniciando pipeline com {} handlers", handlers.size());

        for (Handler h : handlers) {
            log.info("Executando handler: {}", h.getClass().getSimpleName());
            h.handle(ctx);
        }
        log.info("Pipeline finalizado");
        return ctx;

    }

}
