package org.example.csv;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.model.Vuelo;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class LeerCsv {

    


    // Leer todos los vuelos de los csv
   public List<Vuelo> leerTodosLosVuelos(Path datasetDirectory) throws Exception {
        List<Vuelo> vuelos = new ArrayList<>();
        Path VuelosProximos = datasetDirectory.resolve("vuelos_proximos.csv");
        Path vuelosFutururos = datasetDirectory.resolve("vuelos_futuros.csv");

        return vuelos;
    }

    public List<Vuelo> leerVuelosProximos(Path archivo) throws Exception {
       List<Vuelo> vuelos = new ArrayList<>();

        try (CSVParser parser = CSVParser.parse(archivo,StandardCharsets.UTF_8,
                CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build())) {
            for (CSVRecord record : parser) {
                String name = record.get("name").trim();
                String lastName = record.get("lastName").trim();
                String serialNumber = record.get("serialNumber").trim();
                String country = record.get("country").trim();
                String departureDate = record.get("departureDate").trim();
                String departureTime = record.get("departureTime").trim();
                vuelos.add(new Vuelo(name, lastName, serialNumber, country, departureDate, departureTime));
            }
        }

        return vuelos;

    }

    public List<Vuelo> leerVuelosFuturos(Path archivo) throws Exception {

       List<Vuelo> vuelos = new ArrayList<>();
       try (CSVParser parser = CSVParser.parse(archivo, StandardCharsets.UTF_8,
               CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build())) {

           for (CSVRecord record : parser) {
               String fullName = record.get("fullName").trim();
               String serialNumber = record.get("serialNumber").trim();
               String country = record.get("country").trim();
               String dateTime = record.get("dateTime").trim();

               String[] parts = dateTime.split("\\s+");
               String date = parts.length > 0 ? parts[0] : "";
               String time = parts.length > 1 ? parts[1] : "";

               String[] tokens = fullName.trim().split("\\s+");
               String lastname;
               String name;
               if (tokens.length == 1) {
                   name = tokens[0];
                   lastname = "";
               }else {
                   lastname = tokens[tokens.length-1];
                   name = String.join(" ", Arrays.copyOfRange(tokens, 0, tokens.length-1));
               }

               vuelos.add(new Vuelo(name, lastname, serialNumber, country, dateTime, time));
           }

       }

       return vuelos;

    }

}

