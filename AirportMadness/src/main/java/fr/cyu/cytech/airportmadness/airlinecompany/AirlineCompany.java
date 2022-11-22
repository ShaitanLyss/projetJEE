package fr.cyu.cytech.airportmadness.airlinecompany;

import javax.persistence.*;

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
}