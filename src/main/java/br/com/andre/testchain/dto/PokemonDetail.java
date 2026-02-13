package br.com.andre.testchain.dto;

import java.util.List;

public record PokemonDetail(String name, List<PokemonAbility> abilities) {}