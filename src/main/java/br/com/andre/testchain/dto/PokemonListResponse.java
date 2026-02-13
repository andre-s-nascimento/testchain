package br.com.andre.testchain.dto;

import java.util.List;

public record PokemonListResponse(int count, List<NamedAPIResource> results){}
