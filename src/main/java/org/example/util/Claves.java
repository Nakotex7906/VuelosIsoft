package org.example.util;

import org.example.model.Vuelo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public final class Claves {

    private Claves(){}

    // Normaliza nulos y espacios extremos
    private static String nz(String s){
        return s == null ? "" : s.trim().replaceAll(" ", "");
    }

    // construye la clave sin espacios
    public static String buildPlainKey(Vuelo v){
        String name = nz(v.getName());
        String last = nz(v.getLastName());
        String serial = nz(v.getSerialNumber());
        String country = nz(v.getCountry());
        String date = nz(v.getDepartureDate());
        String time = nz(v.getDepartureTime());

        return name + ";" + last + ";" + serial + ";" + country + ";" + date + ";" + time + ";";
    }

    //Contruye todas las claves de una lista de vuelos
    public static List<String> buildPlainKeys(List<Vuelo> vuelos){
        List<String> keys = new ArrayList<>();
        for (Vuelo vuelo : vuelos) {
            keys.add(buildPlainKey(vuelo));
        }
        return keys;
    }

    /** (Opcional) Escribe las claves planas a resultados/claves_planas.txt para verificación */
    public static Path writePlainKeys(Path carpetaResultados, List<Vuelo> vuelos) throws IOException {
        List<String> lines = buildPlainKeys(vuelos);
        Files.createDirectories(carpetaResultados);
        Path out = carpetaResultados.resolve("claves_planas.txt");
        Files.write(out, lines, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        return out;
    }

}
