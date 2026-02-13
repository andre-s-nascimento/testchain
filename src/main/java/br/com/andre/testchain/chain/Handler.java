package br.com.andre.testchain.chain;

import br.com.andre.testchain.context.PokemonContext;

public interface Handler {
    void handle(PokemonContext ctx);
}

