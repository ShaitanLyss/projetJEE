package fr.cyu.airportmadness.entity.person.employee;

import fr.cyu.airportmadness.entity.airlinecompany.AirlineCompany;
import fr.cyu.airportmadness.entity.person.Person;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;


@Entity
public class Employee extends Person {

    @ManyToOne(cascade = {jakarta.persistence.CascadeType.ALL})
    @JoinColumn(name = "airline_company_id")
    @NotNull(message = "Travaille pour quelle compagnie ?")
    private AirlineCompany airlineCompany;

    public AirlineCompany getAirlineCompany() {
        return airlineCompany;
    }

    public Employee setAirlineCompany(AirlineCompany airlineCompany) {
        this.airlineCompany = airlineCompany;
        return this;
    }
}