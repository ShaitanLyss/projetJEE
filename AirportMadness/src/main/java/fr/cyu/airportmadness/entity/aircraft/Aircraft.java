package fr.cyu.airportmadness.entity.aircraft;

import fr.cyu.airportmadness.entity.airlinecompany.AirlineCompany;
import fr.cyu.airportmadness.entity.flight.Flight;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(unique = true)
    private String registration;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "owning_airline_company_id")
    private AirlineCompany owningAirlineCompany;

    @OneToMany(mappedBy = "aircraft", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("time ASC")
    private final Set<Flight> flights = new java.util.LinkedHashSet<>();

    public Set<Flight> getFlights() {
        return flights;
    }


    public Long getId() {
        return id;
    }

    public Aircraft setId(Long id) {
        this.id = id;
        return this;
    }

    public String getRegistration() {
        return registration;
    }

    public Aircraft setRegistration(String registration) {
        this.registration = registration;
        return this;
    }

    public AirlineCompany getOwningAirlineCompany() {
        return owningAirlineCompany;
    }

    public Aircraft setOwningAirlineCompany(AirlineCompany owningAirlineCompany) {
        this.owningAirlineCompany = owningAirlineCompany;
        return this;
    }

}
