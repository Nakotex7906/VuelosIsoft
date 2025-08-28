package org.example.io;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.model.Vuelo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeerCsv {

    //Leer vouelos proximos, (Que ya tienen la forma que piden)
    public List<Vuelo> leerVuelosProximos(Path archivo) throws IOException{
        List<Vuelo> vuelos = new ArrayList<>();

        CSVFormat formato = CSVFormat.DEFAULT.builder()
                .setHeader()  //Primera fila
                .setSkipHeaderRecord(true)
                .setDelimiter(';')
                .setTrim(true)
                .setIgnoreSurroundingSpaces(true)
                .build();

        try (CSVParser parser = CSVParser.parse(archivo, StandardCharsets.UTF_8, formato)) {
            for (CSVRecord record : parser) {
                String name =  record.get("name");
                String lastName = record.get("last_name");
                String serialNumber = record.get("serial_number");
                String country = record.get("country");
                String departureDate = record.get("departure_date");
                String departureTime = record.get("departure_time");

                vuelos.add(new Vuelo(name, lastName, serialNumber, country, departureDate, departureTime));
            }
        }

        return vuelos;

    }

    //Lerr vuelos fututos (Hay que pasarlos al formato final)
    /** Lee vuelos_futuros.csv y lo normaliza al formato final */
    public List<Vuelo> leerVuelosFuturos(Path file) throws IOException {
        List<Vuelo> list = new ArrayList<>();
        if (file == null || !Files.exists(file)) return list;

        CSVFormat fmt = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setDelimiter(';')           // el CSV usa ;
                .setTrim(true)
                .setIgnoreSurroundingSpaces(true)
                .build();

        try (CSVParser parser = CSVParser.parse(file, StandardCharsets.UTF_8, fmt)) {
            for (CSVRecord r : parser) {
                String fullName = r.get("name").trim();            // nombre completo
                String serial = r.get("serial_number").trim();
                String country = r.get("country").trim();
                String dateTime = r.get("date").trim();
                // Separar fecha y hora
                String[] parts = dateTime.split("\\s+");           // [fecha, hora]
                String date = parts.length > 0 ? parts[0] : "";
                String time = parts.length > 1 ? parts[1].replace(" ", "") : "";

                // Separar nombre y apellido (último token = apellido)
                String[] tokens = fullName.split("\\s+");
                String lastName;
                String name;
                if (tokens.length <= 1) {
                    name = fullName;
                    lastName = "";
                } else {
                    lastName = tokens[tokens.length - 1];
                    name = String.join(" ", Arrays.copyOf(tokens, tokens.length - 1));
                }

                list.add(new Vuelo(name, lastName, serial, country, date, time));
            }
        }
        return list;
    }

}
