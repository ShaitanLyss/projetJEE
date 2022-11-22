package fr.cyu.airportmadness.entity.aircraft;

import fr.cyu.airportmadness.entity.airlinecompany.AirlineCompany;
import fr.cyu.airportmadness.entity.flight.Flight;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String registration;

    @ManyToOne
    @JoinColumn(name = "owning_airline_company_id")
    private AirlineCompany owningAirlineCompany;

    @OneToMany(mappedBy = "aircraft", orphanRemoval = true)
    @OrderBy("time ASC")
    private List<Flight> flights = new ArrayList<>();

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public AirlineCompany getOwningAirlineCompany() {
        return owningAirlineCompany;
    }

    public void setOwningAirlineCompany(AirlineCompany owningAirlineCompany) {
        this.owningAirlineCompany = owningAirlineCompany;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }
}
