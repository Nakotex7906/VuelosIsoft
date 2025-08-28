package org.example;

import org.example.io.LeerCsv;
import org.example.io.EscribirCsv;
import org.example.io.ResultadosWriter;
import org.example.model.Vuelo;
import org.example.util.Claves;
import org.example.crypto.KeySquare;
import org.example.crypto.CifradorCoordenadas;
import org.example.crypto.DesplazadorCoordenadas;
import org.example.crypto.Simbolizador;

import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // Args (opcional): <datasets> <resultados> <key.txt>
            Path datasets   = Paths.get(args.length > 0 ? args[0] : "datasets");
            Path resultados = Paths.get(args.length > 1 ? args[1] : "resultados");
            Path keyPath    = Paths.get(args.length > 2 ? args[2] : "datasets/key/key_var1.txt");

            // ===== Paso 1: leer y fusionar, escribir resultados/vuelos.csv =====
            LeerCsv lector = new LeerCsv();
            List<Vuelo> vuelos = new ArrayList<>();
            vuelos.addAll(lector.leerVuelosProximos(datasets.resolve("vuelos_proximos.csv")));
            vuelos.addAll(lector.leerVuelosFuturos(datasets.resolve("vuelos_futuros.csv")));
            new EscribirCsv().escribirVuelos(resultados, vuelos);

            // ===== Paso 2: claves planas (opcional: escríbelas si quieres revisarlas) =====
            List<String> clavesPlanas = Claves.buildPlainKeys(vuelos);
            // Files.write(resultados.resolve("claves_planas.txt"), clavesPlanas, StandardCharsets.UTF_8);

            // ===== Paso 3: plano -> coordenadas (mantener ';', omitir ':' y '-') =====
            KeySquare key = KeySquare.loadFrom(keyPath);
            List<String> clavesEnCoords = new ArrayList<>(clavesPlanas.size());
            for (String plain : clavesPlanas) {
                clavesEnCoords.add(CifradorCoordenadas.aCoordenadas(plain, key));
            }
            // Files.write(resultados.resolve("claves_coords.txt"), clavesEnCoords, StandardCharsets.UTF_8);

            // ===== Paso 4: desplazamiento +3 con wrap 1..6 =====
            List<String> coordsDesplazadas = new ArrayList<>(clavesEnCoords.size());
            for (String c : clavesEnCoords) {
                coordsDesplazadas.add(DesplazadorCoordenadas.desplazarMas3(c));
            }
            // Files.write(resultados.resolve("claves_coords_shifted.txt"), coordsDesplazadas, StandardCharsets.UTF_8);

            // ===== Paso 5: coords desplazadas -> texto cifrado y escribir resultados.txt =====
            List<String> clavesCifradas = new ArrayList<>(coordsDesplazadas.size());
            for (String c : coordsDesplazadas) {
                clavesCifradas.add(Simbolizador.deCoordenadas(c, key));
            }
            new ResultadosWriter().escribirResultados(resultados, clavesCifradas);

            // Resumen
            System.out.println("OK");
            System.out.println("Vuelos fusionados: " + vuelos.size());
            System.out.println("Claves planas: " + clavesPlanas.size());
            System.out.println("A coordenadas: " + clavesEnCoords.size());
            System.out.println("Shift +3: " + coordsDesplazadas.size());
            System.out.println("Cifradas: " + clavesCifradas.size());
            System.out.println("Archivos:");
            System.out.println(" - " + resultados.resolve("vuelos.csv").toAbsolutePath());
            System.out.println(" - " + resultados.resolve("resultados.txt").toAbsolutePath());

        } catch (Exception e) {
            System.err.println("[ERROR] " + e.getMessage());
            System.exit(1);
        }
    }
}
