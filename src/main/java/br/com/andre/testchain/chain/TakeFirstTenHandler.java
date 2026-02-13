package br.com.andre.testchain.chain;

import br.com.andre.testchain.context.PokemonContext;
import br.com.andre.testchain.dto.NamedAPIResource;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class TakeFirstTenHandler implements Handler {
    @Override
    public void handle(PokemonContext ctx) {

        log.info("Passo 1 - Selecionando os 10 primeiros pokémons (lista original: {})",
                ctx.getRawList() != null ? ctx.getRawList().size() : 0);

        List<NamedAPIResource> first10 = ctx.getRawList().stream().limit(10).toList();
        ctx.setFirstTen(first10);
        log.info("Passo 1 - Seleção concluída. Quantidade selecionada: {}", first10.size());
    }

}
