package fr.cyu.airportmadness.entity.booking;

import fr.cyu.airportmadness.entity.flight.Flight;
import fr.cyu.airportmadness.entity.person.passenger.Passenger;
import fr.cyu.airportmadness.entity.person.passenger.customer.Customer;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToMany(mappedBy = "bookings", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private final Set<Passenger> passengers = new LinkedHashSet<>();

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "flight_id")
    private Flight flight;

    public Long getId() {
        return id;
    }

    public Booking setId(Long id) {
        this.id = id;
        return this;
    }



    @Column(name = "num_luggages", nullable = false)
    private Integer numLuggages;

    @Column(name = "price", nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    public Set<Passenger> getPassengers() {
        return passengers;
    }

    public Booking addPassenger(Passenger passenger) {
        this.passengers.add(passenger);
        passenger.addBookings(this);
        return this;
    }

    public Booking addPassengers(Passenger... passengers) {
        this.passengers.addAll(List.of(passengers));
        for (Passenger p :
                passengers) {
            p.addBookings(this);

        }
        return this;
    }

    public Booking removePassenger(Passenger passenger) {
        this.passengers.remove(passenger);
        return this;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Booking setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public Flight getFlight() {
        return flight;
    }

    public Booking setFlight(Flight flight) {
        this.flight = flight;
        return this;
    }

    public Integer getNumLuggages() {
        return numLuggages;
    }

    public Booking setNumLuggages(Integer numLuggages) {
        this.numLuggages = numLuggages;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Booking setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}