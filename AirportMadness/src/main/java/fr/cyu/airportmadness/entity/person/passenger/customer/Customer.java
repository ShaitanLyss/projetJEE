package fr.cyu.airportmadness.entity.person.passenger.customer;

import fr.cyu.airportmadness.entity.booking.Booking;
import fr.cyu.airportmadness.entity.person.Gender;
import fr.cyu.airportmadness.entity.person.passenger.PaperType;
import fr.cyu.airportmadness.entity.person.passenger.Passenger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Customer extends Passenger {
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "customer", cascade = javax.persistence.CascadeType.ALL, orphanRemoval = true)
    private final Set<Booking> createdBookings = new java.util.LinkedHashSet<>();

    public Set<Booking> getCreatedBookings() {
        return createdBookings;
    }

    public Customer() {
    }

    public Customer(String firstName, String lastName, LocalDate birthdate, Gender gender, String nationality, String numIdentityCard, PaperType paperType, String phoneNumber, String email) {
        super(firstName, lastName, birthdate, gender, nationality, numIdentityCard, paperType);
        this.phoneNumber = phoneNumber;
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}