package org.example.model;

public class Vuelo {

    private String name;
    private String lastName;
    private String serialNumber;
    private String country;
    private String departureDate;
    private String departureTime;

    public Vuelo(String name, String lastName, String serialNumber, String country, String departureDate, String departureTime) {
        this.name = name;
        this.lastName = lastName;
        this.serialNumber = serialNumber;
        this.country = country;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
    }

    public String getCountry() {
        return country;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return name;
    }
}
