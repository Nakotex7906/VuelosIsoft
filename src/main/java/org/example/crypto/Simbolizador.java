package org.example.crypto;

public final class Simbolizador {
    private Simbolizador() {}

    /** Convierte "1122;3344" a "AB;CD" usando la key. Mantiene ';' y agrupa pares 1..6. */
    public static String deCoordenadas(String coords, KeySquare key) {
        if (coords == null) return "";
        StringBuilder out = new StringBuilder(coords.length());
        StringBuilder par = new StringBuilder(2);

        for (int i = 0; i < coords.length(); i++) {
            char ch = coords.charAt(i);

            if (ch == ';') {
                par.setLength(0);     // descarta dígito suelto, si lo hubiera
                out.append(';');
                continue;
            }

            if (ch >= '1' && ch <= '6') {
                par.append(ch);
                if (par.length() == 2) {
                    out.append(key.charAt(par.toString()));
                    par.setLength(0);
                }
            } // cualquier otro carácter se ignora
        }
        // si queda un dígito colgado al final, se ignora
        return out.toString();
    }
}
