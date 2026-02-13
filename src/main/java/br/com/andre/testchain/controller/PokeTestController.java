package br.com.andre.testchain.controller;

import br.com.andre.testchain.context.PokemonContext;
import br.com.andre.testchain.feign.PokeApiClient;
import br.com.andre.testchain.service.PokemonPipelineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/poketest")
public class PokeTestController {

    private final PokeApiClient client;
    private final PokemonPipelineService pipeline;

    public PokeTestController(PokeApiClient client, PokemonPipelineService pipeline) {
        this.client = client;
        this.pipeline = pipeline;
    }

    @GetMapping
    public ResponseEntity<?> run() {
        //        log.info("Recebida requisição para /v1/poketest");
        //        // 1) Buscar lista (poderia ser >10; a cadeia fará o corte para 10)
        //        PokemonListResponse list = client.getPokemonList(100, 0);
        //
        //        // 2) Colocar no contexto
        //        PokemonContext ctx = new PokemonContext();
        //        ctx.setRawList(list.results());
        //
        //        // 3) Executar pipeline (10 primeiros -> enriquecer -> salvar JSON)
        //        pipeline.process(ctx);
        //        log.info("Processamento concluído. Retornando {} itens.", ctx.getProcessed().size());
        //        // 4) Responder com a lista processada
        //        return ResponseEntity.ok(ctx.getProcessed());
        //    }

        log.info("Recebida requisição para /v1/poketest (random 10)");
        var ctx = new PokemonContext();
        pipeline.process(ctx);
        log.info("Processamento concluído. Retornando {} itens.", ctx.getProcessed().size());
        return ResponseEntity.ok(ctx.getProcessed());

    }
}

