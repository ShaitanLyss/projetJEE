package fr.cyu.airportmadness.entity.airlinecompany;

import fr.cyu.airportmadness.entity.aircraft.Aircraft;
import fr.cyu.airportmadness.entity.airline.Airline;
import fr.cyu.airportmadness.entity.person.employee.Employee;

import fr.cyu.airportmadness.security.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "airline_company")
public class AirlineCompany {
    @Override
    public String toString() {
        return "AirlineCompany{" +
                "name='" + name + '\'' +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "airlineCompany", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotNull(message = "Selectionner au moins une ligne de vol pour cette compagnie")
    private final Set<Airline> airlines = new LinkedHashSet<>();

    @OneToMany(mappedBy = "airlineCompany", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Employee> employees = new LinkedHashSet<>();

    @Column(name = "name")
    @NotBlank(message = "Votre compagnie doit avoir un nom")
    private String name;

    @OneToMany(mappedBy = "owningAirlineCompany", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotNull
    private final Set<Aircraft> aircrafts = new LinkedHashSet<>();

    @OneToOne(mappedBy = "airlineCompany", cascade = CascadeType.ALL, orphanRemoval = true)
    private User user;

    public User getUser() {
        return user;
    }

    public AirlineCompany setUser(User user) {
        this.user = user;
        user.setAirlineCompany(this);
        this.user.setRole("AIRLINE");
        return this;
    }

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
        addAircrafts(List.of(aircrafts));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AirlineCompany that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public AirlineCompany addAircrafts(List<Aircraft> aircrafts) {
        this.aircrafts.addAll(aircrafts);
        this.aircrafts.forEach(aircraft -> aircraft.setOwningAirlineCompany(this));
        return this;
    }
}