package br.com.andre.testchain.chain;

import br.com.andre.testchain.context.PokemonContext;
import br.com.andre.testchain.domain.PokemonProcessed;
import br.com.andre.testchain.dto.PokemonDetail;
import br.com.andre.testchain.feign.PokeApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
public class EnrichAbilitiesHandler implements Handler {

    private final PokeApiClient client;

    public EnrichAbilitiesHandler(PokeApiClient client) {
        this.client = client;
    }

    @Override
    public void handle(PokemonContext ctx) {

        log.info("Passo 2 - Enriquecendo com habilidades para {} pokémons",
                ctx.getFirstTen() != null ? ctx.getFirstTen().size() : 0);

        var processed = ctx.getFirstTen().stream().map(item -> {
            PokemonDetail detail = client.getPokemon(item.name());
            List<String> abilities = detail.abilities().stream()
                    .map(a -> a.ability().name())
                    .toList();
            log.info("Passo 2 - [{}] habilidades: {}", detail.name(), abilities);
            return new PokemonProcessed(detail.name(), abilities, OffsetDateTime.now());
        }).toList();

        ctx.setProcessed(processed);
        log.info("Passo 2 - Enriquecimento concluído. Itens processados: {}", processed.size());
    }
}

