package fr.cyu.airportmadness.entity.airlinecompany;

import fr.cyu.airportmadness.entity.aircraft.Aircraft;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "airline_company")
public class AirlineCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "owningAirlineCompany", orphanRemoval = true)
    private List<Aircraft> aircrafts = new ArrayList<>();

    public List<Aircraft> getAircrafts() {
        return aircrafts;
    }

    public void setAircrafts(List<Aircraft> aircrafts) {
        this.aircrafts = aircrafts;
    }
}