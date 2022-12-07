package fr.cyu.airportmadness.entity.airport;

import fr.cyu.airportmadness.entity.airline.Airline;
import fr.cyu.airportmadness.entity.city.City;

import jakarta.persistence.*;
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
    private String name;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "departure", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Airline> departingAirlines = new LinkedHashSet<>();

    @OneToMany(mappedBy = "departure", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Airline> arrivingAirlines = new LinkedHashSet<>();

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
}