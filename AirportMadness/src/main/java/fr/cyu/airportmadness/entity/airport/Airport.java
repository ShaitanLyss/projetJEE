package fr.cyu.airportmadness.entity.airport;

import fr.cyu.airportmadness.entity.airline.Airline;
import fr.cyu.airportmadness.entity.city.City;

import fr.cyu.airportmadness.entity.country.Country;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;
import org.locationtech.jts.geom.Point;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "airport")
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    @NotNull(message = "Votre aéroport doit avoir un nom")
    private String name;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "city_id", nullable = true)
    private City city;

    @Column(name = "latitude")
    @NotNull
    private Double latitude;

    @Column(name = "longitude")
    @NotNull
    private double longitude;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id")
    @NotNull(message = "Un pays doit être sélectionné")
    private Country country;

    @OneToMany(mappedBy = "departure", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Airline> departingAirlines = new LinkedHashSet<>();

    @OneToMany(mappedBy = "departure", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Airline> arrivingAirlines = new LinkedHashSet<>();

    @Column(name = "type")
    private String type;

    @SuppressWarnings("com.haulmont.jpb.UnsupportedTypeWithoutConverterInspection")
    @Column(name = "location")
    @JdbcTypeCode(SqlTypes.GEOGRAPHY)
    private Point location;

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Country getCountry() {
        return country;
    }

    public Airport setCountry(Country country) {
        this.country = country;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public Airport setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Airport setLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Airport setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Airport setName(String name) {
        this.name = name;
        return this;
    }


    public City getCity() {
        return city;
    }

    public Airport setCity(City city) {
        this.city = city;
        return this;
    }

    public Set<Airline> getDepartingAirlines() {
        return departingAirlines;
    }

    public Set<Airline> getArrivingAirlines() {
        return arrivingAirlines;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "name='" + name + '\'' +
                ", city=" + city +
                '}';
    }
}