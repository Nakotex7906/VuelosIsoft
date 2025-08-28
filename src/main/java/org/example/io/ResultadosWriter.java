// src/main/java/org/example/io/ResultadosWriter.java
package org.example.io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

public class ResultadosWriter {
    public void escribirResultados(Path carpetaResultados, List<String> clavesCifradas) throws IOException {
        Files.createDirectories(carpetaResultados);
        Path out = carpetaResultados.resolve("resultados.txt");

        var lines = new java.util.ArrayList<String>();
        lines.add("### CASOS ACTIVOS MENSUALES POR PAÍS ###");
        lines.add("### INICIO ARCHIVO ###");
        lines.addAll(clavesCifradas);
        lines.add("### FIN ARCHIVO ###");

        Files.write(out, lines, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
