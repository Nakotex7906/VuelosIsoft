package org.example.crypto;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class KeySquare {
    private final Map<Character, String> toCoords = new HashMap<>(); // 'A' -> "11"
    private final Map<String, Character> fromCoords = new HashMap<>(); // "11" -> 'A'

    private KeySquare() {}

    /** Carga datasets/key.txt (6x6) ignorando encabezados "1 2 3 4 5 6" y los índices de fila. */
    public static KeySquare loadFrom(Path keyTxt) throws IOException {
        if (keyTxt == null || !Files.exists(keyTxt)) {
            throw new IOException("No se encontró key.txt en: " + keyTxt);
        }

        List<String> lines = Files.readAllLines(keyTxt, StandardCharsets.UTF_8);
        List<Character> symbols = new ArrayList<>(36);

        boolean headerSkipped = false;
        for (String raw : lines) {
            String line = raw.trim();
            if (line.isEmpty()) continue;
            if (line.contains("BEGIN") || line.contains("END")) continue;

            String[] toks = line.split("\\s+");

            // Salta el encabezado "1 2 3 4 5 6"
            if (!headerSkipped) {
                boolean allDigits = true;
                for (String t : toks) {
                    if (!t.matches("[1-6]")) { allDigits = false; break; }
                }
                if (allDigits) { headerSkipped = true; continue; }
            }

            // Filas tipo "1 A B C D E F": ignora el primer token (índice de fila)
            if (toks[0].matches("[1-6]")) {
                for (int j = 1; j < toks.length && symbols.size() < 36; j++) {
                    String t = toks[j];
                    if (t.matches("[A-Z0-9]")) {
                        symbols.add(t.charAt(0));
                    }
                }
            }
        }

        if (symbols.size() != 36) {
            throw new IOException("Key inválida: esperados 36 símbolos, leídos: " + symbols.size());
        }

        KeySquare ks = new KeySquare();
        int idx = 0;
        for (int fila = 1; fila <= 6; fila++) {
            for (int col = 1; col <= 6; col++) {
                char c = symbols.get(idx++);
                String cc = "" + fila + col;
                ks.toCoords.put(c, cc);
                ks.fromCoords.put(cc, c);
            }
        }
        return ks;
    }

    public boolean contains(char c) { return toCoords.containsKey(c); }
    public String coordsOf(char c) { return toCoords.get(c); }

    /** Devuelve el carácter para un par "rc" (r y c en '1'..'6'). */
    public char charAt(String twoDigits) {
        Character ch = fromCoords.get(twoDigits);
        if (ch == null) throw new IllegalArgumentException("Par inválido: " + twoDigits);
        return ch;
    }
}
