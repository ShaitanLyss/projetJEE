package fr.cyu.cytech.airportmadness.airlinecompany;

import fr.cyu.cytech.airportmadness.airplane.Airplane;

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
    private List<Airplane> airplanes = new ArrayList<>();

    public List<Airplane> getAirplanes() {
        return airplanes;
    }

    public void setAirplanes(List<Airplane> airplanes) {
        this.airplanes = airplanes;
    }
}