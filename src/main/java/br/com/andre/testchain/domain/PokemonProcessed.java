package br.com.andre.testchain.domain;

import java.time.OffsetDateTime;
import java.util.List;

public record PokemonProcessed(String name, List<String> abilities, OffsetDateTime createdAt) {}
