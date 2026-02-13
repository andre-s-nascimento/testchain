package br.com.andre.testchain.chain;

import br.com.andre.testchain.context.PokemonContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Order(30)
@Slf4j
public class SaveJsonHandler implements Handler {

    private static final DateTimeFormatter TS = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

    private final ObjectMapper mapper;
    private final Path outputDir;

    public SaveJsonHandler(ObjectMapper mapper,
                           @Value("${app.output-dir}") String outputDir) {

        // Jackson 3: ObjectMapper é imutável; use rebuild() para ajustar config
        this.mapper = mapper.rebuild()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .build();

        this.outputDir = Path.of(outputDir);

    }

    @Override
    public void handle(PokemonContext ctx) {

        // Gera o nome: pokemons-YYYYMMDD-HHmmss.json
        String filename = "pokemons-" + LocalDateTime.now().format(TS) + ".json";
        Path outputFile = outputDir.resolve(filename);

        log.info("Passo 3 - Salvando JSON em: {}", outputFile.toAbsolutePath());
        try {
            if (outputFile.getParent() != null) {
                Files.createDirectories(outputFile.getParent());
            }
            mapper.writeValue(outputFile.toFile(), ctx.getProcessed());
            ctx.setOutputFile(outputFile);

            log.info("Passo 3 - Arquivo gerado com sucesso ({} itens)",
                    ctx.getProcessed() != null ? ctx.getProcessed().size() : 0);

        } catch (IOException e) {
            log.error("Passo 3 - Falha ao salvar JSON em {}", outputFile, e);
            throw new RuntimeException("Erro ao salvar JSON em " + outputFile, e);

        }
    }

}

