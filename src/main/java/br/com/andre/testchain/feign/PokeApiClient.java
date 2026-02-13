package br.com.andre.testchain.feign;

import br.com.andre.testchain.dto.PokemonDetail;
import br.com.andre.testchain.dto.PokemonListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

// Base da PokéAPI v2
@FeignClient(name = "pokeapi", url = "https://pokeapi.co/api/v2")
public interface PokeApiClient {

    // Lista paginada de pokemons (nome + url)
    @GetMapping("/pokemon")
    PokemonListResponse getPokemonList(@RequestParam int limit, @RequestParam int offset);


    // Buscar detalhe por ID
    @GetMapping("/pokemon/{id}")
    PokemonDetail getPokemonById(@PathVariable int id);

    // Buscar detalhe por nome (opcional):
    @GetMapping("/pokemon/{name}")
    PokemonDetail getPokemon(@PathVariable String name);

}

