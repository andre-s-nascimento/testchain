package br.com.andre.testchain.context;

import br.com.andre.testchain.domain.PokemonProcessed;
import br.com.andre.testchain.dto.NamedAPIResource;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PokemonContext {

    private List<NamedAPIResource> rawList = new ArrayList<>();
    private List<NamedAPIResource> firstTen = new ArrayList<>();
    private List<PokemonProcessed> processed = new ArrayList<>();
    private Path outputFile;

    public List<NamedAPIResource> getRawList() { return rawList; }
    public void setRawList(List<NamedAPIResource> rawList) { this.rawList = rawList; }

    public List<NamedAPIResource> getFirstTen() { return firstTen; }
    public void setFirstTen(List<NamedAPIResource> firstTen) { this.firstTen = firstTen; }

    public List<PokemonProcessed> getProcessed() { return processed; }
    public void setProcessed(List<PokemonProcessed> processed) { this.processed = processed; }

    public Path getOutputFile() { return outputFile; }
    public void setOutputFile(Path outputFile) { this.outputFile = outputFile; }
}

