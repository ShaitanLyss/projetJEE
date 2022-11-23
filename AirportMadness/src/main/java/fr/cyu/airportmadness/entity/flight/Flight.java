package fr.cyu.airportmadness.entity.flight;

import fr.cyu.airportmadness.entity.aircraft.Aircraft;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "flight")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime datetime) {
        this.time = datetime;
    }

    @ManyToOne
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }
}