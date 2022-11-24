package fr.cyu.airportmadness.entity.airport;

import fr.cyu.airportmadness.entity.airline.Airline;
import fr.cyu.airportmadness.entity.city.City;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "airport")
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "airports")
    private Set<City> cities = new LinkedHashSet<>();

    @OneToMany(mappedBy = "departure", orphanRemoval = true)
    private Set<Airline> departingAirlines = new LinkedHashSet<>();

    @OneToMany(mappedBy = "departure", orphanRemoval = true)
    private Set<Airline> arrivingAirlines = new LinkedHashSet<>();

    public Set<Airline> getArrivingAirlines() {
        return arrivingAirlines;
    }

    public void setArrivingAirlines(Set<Airline> arrivingAirlines) {
        this.arrivingAirlines = arrivingAirlines;
    }

    public Set<Airline> getDepartingAirlines() {
        return departingAirlines;
    }

    public void setDepartingAirlines(Set<Airline> departingAirlines) {
        this.departingAirlines = departingAirlines;
    }

    public Set<City> getCities() {
        return cities;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}