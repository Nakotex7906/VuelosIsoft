package org.example.io;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.example.model.Vuelo;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class EscribirCsv {

    /** Escribe resultados/vuelos.csv con el esquema requerido (usando ';') */
    public void escribirVuelos(Path carpetaResultados, List<Vuelo> vuelos) throws IOException {
        if (carpetaResultados == null) throw new IllegalArgumentException("carpetaResultados == null");
        Files.createDirectories(carpetaResultados);

        Path out = carpetaResultados.resolve("vuelos.csv");

        CSVFormat outFmt = CSVFormat.DEFAULT.builder()
                .setHeader("name","last_name","serial_number","country","departure_date","departure_time")
                .setDelimiter(';')   // CLAVE: escribe con ';' como en el origen
                .build();

        try (var writer = new OutputStreamWriter(
                Files.newOutputStream(out, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING),
                StandardCharsets.UTF_8);
             var csv = new CSVPrinter(writer, outFmt)) {

            for (Vuelo v : vuelos) {
                csv.printRecord(
                        v.getName(),
                        v.getLastName(),
                        v.getSerialNumber(),
                        v.getCountry(),
                        v.getDepartureDate(),
                        v.getDepartureTime()
                );
            }
        }
    }

    /** Conveniencia: lee, fusiona y escribe en un solo paso (Paso 1 completo) */
    public void fusionarYEscribir(Path carpetaDatasets, Path carpetaResultados) throws Exception {
        if (carpetaDatasets == null) throw new IllegalArgumentException("carpetaDatasets == null");
        if (carpetaResultados == null) throw new IllegalArgumentException("carpetaResultados == null");

        LeerCsv lector = new LeerCsv();
        List<Vuelo> todos = new ArrayList<>();

        Path proximos = carpetaDatasets.resolve("vuelos_proximos.csv");
        if (Files.exists(proximos)) {
            todos.addAll(lector.leerVuelosProximos(proximos));
        }

        Path futuros = carpetaDatasets.resolve("vuelos_futuros.csv");
        if (Files.exists(futuros)) {
            todos.addAll(lector.leerVuelosFuturos(futuros));
        }

        escribirVuelos(carpetaResultados, todos);
    }
}
