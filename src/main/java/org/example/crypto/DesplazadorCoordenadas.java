package org.example.crypto;

public final class DesplazadorCoordenadas {
    private DesplazadorCoordenadas() {}

    /** Desplaza cada dígito 1..6 sumando 'k' con wrap en 6. Mantiene ';' tal cual. */
    public static String desplazar(String coords, int k) {
        if (coords == null) return "";
        StringBuilder sb = new StringBuilder(coords.length());
        for (int i = 0; i < coords.length(); i++) {
            char ch = coords.charAt(i);
            if (ch == ';') {                // mantener separadores de campos
                sb.append(';');
            } else if (ch >= '1' && ch <= '6') {
                int d = ch - '0';           // 1..6
                int shifted = ((d - 1 + k) % 6) + 1;  // wrap 1..6
                sb.append(shifted);
            } else {
                // ignorar cualquier otro carácter (no debería haber)
            }
        }
        return sb.toString();
    }

    /** Conveniencia específica del enunciado: +3 */
    public static String desplazarMas3(String coords) {
        return desplazar(coords, 3);
    }
}
