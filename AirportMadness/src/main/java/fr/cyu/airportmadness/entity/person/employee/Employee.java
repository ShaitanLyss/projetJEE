package fr.cyu.airportmadness.entity.person.employee;

import fr.cyu.airportmadness.entity.airlinecompany.AirlineCompany;
import fr.cyu.airportmadness.entity.person.Person;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class Employee extends Person {

    @ManyToOne(cascade = {jakarta.persistence.CascadeType.ALL})
    @JoinColumn(name = "airline_company_id")
    private AirlineCompany airlineCompany;

    public AirlineCompany getAirlineCompany() {
        return airlineCompany;
    }

    public Employee setAirlineCompany(AirlineCompany airlineCompany) {
        this.airlineCompany = airlineCompany;
        return this;
    }
}