package fr.cyu.airportmadness.entity.person.passenger.customer;

import fr.cyu.airportmadness.entity.booking.Booking;
import fr.cyu.airportmadness.entity.person.Gender;
import fr.cyu.airportmadness.entity.person.passenger.PaperType;
import fr.cyu.airportmadness.entity.person.passenger.Passenger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

@Entity
public class Customer extends Passenger {
    @Column(name = "phone_number")
    @NotNull(message = "Entrer un numéro de téléphone")
    private String phoneNumber;

    @Column(name = "email")
    @NotNull(message = "Entrer une adresse mail")
    private String email;

    @OneToMany(mappedBy = "customer", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
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