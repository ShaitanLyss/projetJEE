package fr.cyu.airportmadness.entity.airline;

import fr.cyu.airportmadness.entity.airlinecompany.AirlineCompany;
import fr.cyu.airportmadness.entity.airport.Airport;
import fr.cyu.airportmadness.entity.flight.Flight;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "airline")
public class Airline {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "departure_id", nullable = false)
    private Airport departure;

    @ManyToOne(optional = false)
    @JoinColumn(name = "arrival_id", nullable = false)
    private Airport arrival;

    @ManyToOne(optional = false)
    @JoinColumn(name = "airline_company_id", nullable = false)
    private AirlineCompany airlineCompany;

    @OneToMany(mappedBy = "airline", orphanRemoval = true)
    @OrderBy("time ASC")
    private List<Flight> flights = new ArrayList<>();

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public AirlineCompany getAirlineCompany() {
        return airlineCompany;
    }

    public void setAirlineCompany(AirlineCompany airlineCompany) {
        this.airlineCompany = airlineCompany;
    }

    public Airport getArrival() {
        return arrival;
    }

    public void setArrival(Airport arrival) {
        this.arrival = arrival;
    }

    public Airport getDeparture() {
        return departure;
    }

    public void setDeparture(Airport departure) {
        this.departure = departure;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}