package fr.cyu.airportmadness.entity.airlinecompany;

import fr.cyu.airportmadness.entity.aircraft.Aircraft;
import fr.cyu.airportmadness.entity.airline.Airline;
import fr.cyu.airportmadness.entity.person.employee.Employee;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "airline_company")
public class AirlineCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "airlineCompany", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Airline> airlines = new LinkedHashSet<>();

    @OneToMany(mappedBy = "airlineCompany", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Employee> employees = new LinkedHashSet<>();

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "owningAirlineCompany", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Aircraft> aircrafts = new LinkedHashSet<>();

    public Set<Airline> getAirlines() {
        return airlines;
    }

    public AirlineCompany addAirlines(Airline... airlines) {
        this.airlines.addAll(List.of(airlines));
        this.airlines.forEach(airline -> airline.setAirlineCompany(this));
        return this;
    }

    public Set<Aircraft> getAircrafts() {
        return aircrafts;
    }

    public AirlineCompany addAircrafts(Aircraft... aircrafts) {
        this.aircrafts.addAll(List.of(aircrafts));
        this.aircrafts.forEach(aircraft -> aircraft.setOwningAirlineCompany(this));
        return this;
    }

    public Long getId() {
        return id;
    }

    public AirlineCompany setId(Long id) {
        this.id = id;
        return this;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public  AirlineCompany addEmployees(Employee... employees) {
        this.employees.addAll(List.of(employees));
        this.employees.forEach(employee -> employee.setAirlineCompany(this));
        return this;
    }

    public String getName() {
        return name;
    }

    public AirlineCompany setName(String name) {
        this.name = name;
        return this;
    }

}