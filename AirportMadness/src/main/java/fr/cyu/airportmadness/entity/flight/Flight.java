package fr.cyu.airportmadness.entity.flight;

import fr.cyu.airportmadness.entity.aircraft.Aircraft;
import fr.cyu.airportmadness.entity.airline.Airline;
import fr.cyu.airportmadness.entity.booking.Booking;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "flight")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "airline_id", nullable = false)
    private Airline airline;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Booking> bookings = new LinkedHashSet<>();


    @Column(name = "time")
    private LocalDateTime time;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;

    public Long getId() {
        return id;
    }

    public Flight setId(Long id) {
        this.id = id;
        return this;
    }

    public Airline getAirline() {
        return airline;
    }

    public Flight setAirline(Airline airline) {
        this.airline = airline;
        return this;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Flight setTime(LocalDateTime time) {
        this.time = time;
        return this;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public Flight setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
        return this;
    }
}