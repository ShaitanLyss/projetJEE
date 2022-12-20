package fr.cyu.airportmadness.datasets;

import com.opencsv.bean.CsvBindByName;

public class CsvAirport {
    // "id","ident","type",
    // "name","latitude_deg","longitude_deg",
    // "elevation_ft","continent","iso_country",
    // "iso_region","municipality","scheduled_service",
    // "gps_code","iata_code","local_code","home_link",
    // "wikipedia_link","keywords"

    @Override
    public String toString() {
        return "CsvAirport{" +
                "municipality='" + municipality + '\'' +
                ", name='" + name + '\'' +
                ", iso_country='" + iso_country + '\'' +
                '}';
    }

    @CsvBindByName
    private String municipality;

    @CsvBindByName
    public String scheduled_service;

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLatitude_deg() {
        return latitude_deg;
    }

    public void setLatitude_deg(double latitude_deg) {
        this.latitude_deg = latitude_deg;
    }

    public double getLongitude_deg() {
        return longitude_deg;
    }

    public void setLongitude_deg(double longitude_deg) {
        this.longitude_deg = longitude_deg;
    }

    public String getIso_country() {
        return iso_country;
    }

    public void setIso_country(String iso_country) {
        this.iso_country = iso_country;
    }

    @CsvBindByName
    private String name;

    @CsvBindByName
    private String type;

    @CsvBindByName
    private double latitude_deg;

    @CsvBindByName
    private double longitude_deg;

    @CsvBindByName
    private String iso_country;



}
