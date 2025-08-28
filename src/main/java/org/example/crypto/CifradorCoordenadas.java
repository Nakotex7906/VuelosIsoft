package org.example.crypto;

import java.text.Normalizer;

public class CifradorCoordenadas {

    /** Convierte la clave plana a coordenadas:
     *  - Mantiene ';'
     *  - Omite ':' y '-'
     *  - Ignora lo que no esté en la key (espacios, comas, etc.)
     *  - Normaliza: mayúsculas, tildes fuera, Ñ→N
     */
    public static String aCoordenadas(String clavePlana, KeySquare key) {
        if (clavePlana == null) return "";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < clavePlana.length(); i++) {
            char ch = clavePlana.charAt(i);

            if (ch == ';') { sb.append(';'); continue; }
            if (ch == ':' || ch == '-') { continue; }

            char up = normalizarChar(ch); // MAYÚSCULA + sin diacríticos + Ñ→N
            if (key.contains(up)) {
                sb.append(key.coordsOf(up));
            }
        }
        return sb.toString();
    }

    private static char normalizarChar(char c) {
        char up = Character.toUpperCase(c);
        if (up == 'Ñ') up = 'N';
        String s = String.valueOf(up);
        s = Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", ""); // quita tildes
        return s.charAt(0);
    }
}
