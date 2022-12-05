package fr.cyu.airportmadness.entity.country;

import fr.cyu.airportmadness.entity.city.City;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    public Country() {
    }

    public Country(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private final Set<City> cities = new LinkedHashSet<>();

    public Set<City> getCities() {
        return cities;
    }


    public String getName() {
        return name;
    }

    public Country setName(String name) {
        this.name = name;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Country setId(Long id) {
        this.id = id;
        return this;
    }

}