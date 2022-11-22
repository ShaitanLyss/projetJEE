package fr.cyu.cytech.airportmadness.airplane;

import fr.cyu.cytech.airportmadness.airlinecompany.AirlineCompany;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Airplane {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String registration;

    @ManyToOne
    @JoinColumn(name = "owning_airline_company_id")
    private AirlineCompany owningAirlineCompany;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Airplane airplane = (Airplane) o;
        return id != null && Objects.equals(id, airplane.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
