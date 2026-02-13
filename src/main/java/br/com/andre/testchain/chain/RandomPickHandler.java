package br.com.andre.testchain.chain;

import br.com.andre.testchain.context.PokemonContext;
import br.com.andre.testchain.domain.PokemonProcessed;
import br.com.andre.testchain.dto.PokemonDetail;
import br.com.andre.testchain.dto.PokemonListResponse;
import br.com.andre.testchain.feign.PokeApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.util.IntSummaryStatistics;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@Order(10)
public class RandomPickHandler implements Handler {

    private final PokeApiClient client;
    private final Random rng = new SecureRandom();

    public RandomPickHandler(PokeApiClient client) {
        this.client = client;
    }

    @Override
    public void handle(PokemonContext ctx) {
        log.info("Passo 1 - Lendo 'count' para sorteio por offset");

        PokemonListResponse head = client.getPokemonList(1, 0); // usa só o count
        int count = head.count(); // total de registros na listagem
        log.info("Passo 1 - Total informado pela API: {}", count);


        // Sorteia 10 offsets únicos em [0..count-1]
        var offsets = new LinkedHashSet<Integer>();
        while (offsets.size() < 10) {
            offsets.add(rng.nextInt(Math.max(count, 1)));
        }
        log.info("Passo 1 - Offsets sorteados: {}", offsets);

        // Para cada offset, pega 1 item (name) e busca o detalhe por nome
        var processed = offsets.stream().map(offset -> {
                    var page = client.getPokemonList(1, offset);  // /pokemon?limit=1&offset=X
                    if (page.results() == null || page.results().isEmpty()) {
                        log.warn("Passo 1 - Página vazia no offset {}, ignorando.", offset);
                        return null;
                    }
                    String name = page.results().get(0).name();
                    PokemonDetail detail = client.getPokemon(name); // /pokemon/{name}
                    List<String> abilities = detail.abilities().stream()
                            .map(a -> a.ability().name())
                            .toList();
                    log.info("Passo 1 - [offset={}] {} -> abilities={}", offset, detail.name(), abilities);
                    return new PokemonProcessed(detail.name(), abilities, OffsetDateTime.now());
                })
                .filter(p -> p != null)
                .toList();

        ctx.setProcessed(processed);
        log.info("Passo 1 - Seleção aleatória concluída. Itens processados: {}", processed.size());
        // já deixa pronto para os próximos handlers
    }
}
