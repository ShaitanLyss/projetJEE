package fr.cyu.airportmadness.entity.airline;

import fr.cyu.airportmadness.entity.airlinecompany.AirlineCompany;
import fr.cyu.airportmadness.entity.airport.Airport;
import fr.cyu.airportmadness.entity.flight.Flight;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "airline")
public class Airline {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "departure_id")
    @NotNull(message = "l'aéroport de départ doit être selectionné")
    private Airport departure;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "arrival_id")
    @NotNull(message = "l'aéroport d'arrivée doit être selectionné")
    private Airport arrival;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "airline_company_id")
    @NotNull(message = "la compagnie doit être selectionnée")
    private AirlineCompany airlineCompany;

    @OneToMany(mappedBy = "airline", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("time ASC")
    @NotNull(message = "Cette ligne doit avoir au moins un vol")
    private final Set<Flight> flights = new java.util.LinkedHashSet<>();

    public Set<Flight> getFlights() {
        return flights;
    }

    public Long getId() {
        return id;
    }

    public Airline setId(Long id) {
        this.id = id;
        return this;
    }

    public Airport getDeparture() {
        return departure;
    }

    public Airline setDeparture(Airport departure) {
        this.departure = departure;
        return this;
    }

    public Airport getArrival() {
        return arrival;
    }

    public Airline setArrival(Airport arrival) {
        this.arrival = arrival;
        return this;
    }

    public AirlineCompany getAirlineCompany() {
        return airlineCompany;
    }

    public Airline setAirlineCompany(AirlineCompany airlineCompany) {
        this.airlineCompany = airlineCompany;
        return this;
    }

    @Override
    public String toString() {
        return "Airline{" +
                "departure=" + departure +
                ", arrival=" + arrival +
                ", airlineCompany=" + airlineCompany +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Airline airline)) return false;
        return Objects.equals(departure, airline.departure) && Objects.equals(arrival, airline.arrival) && Objects.equals(airlineCompany, airline.airlineCompany);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departure, arrival, airlineCompany);
    }

    public Airline addFlight(Flight flight) {
        flights.add(flight);
        flight.setAirline(this);

        return this;
    }
}