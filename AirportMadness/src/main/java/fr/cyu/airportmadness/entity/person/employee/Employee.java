package fr.cyu.airportmadness.entity.person.employee;

import fr.cyu.airportmadness.entity.airlinecompany.AirlineCompany;
import fr.cyu.airportmadness.entity.person.Person;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.*;


@Entity
public class Employee extends Person {

    @ManyToOne(cascade = {javax.persistence.CascadeType.ALL})
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