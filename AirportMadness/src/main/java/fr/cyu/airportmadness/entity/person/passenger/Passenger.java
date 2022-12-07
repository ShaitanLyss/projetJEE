package fr.cyu.airportmadness.entity.person.passenger;

import fr.cyu.airportmadness.entity.booking.Booking;
import fr.cyu.airportmadness.entity.person.Gender;
import fr.cyu.airportmadness.entity.person.Person;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
public class Passenger extends Person {
    @Column(name = "num_identity_card")
    private String numIdentityCard;

    @Enumerated(EnumType.STRING)
    @Column(name = "paper_type")
    private PaperType paperType;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "passenger_booking",
            joinColumns = @JoinColumn(name = "passenger_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "booking_id", referencedColumnName = "id"))
    private final Set<Booking> bookings = new java.util.LinkedHashSet<>();

    public Set<Booking> getBookings() {
        return bookings;
    }

    public Passenger() {
    }

    public Passenger(String firstName, String lastName, LocalDate birthdate, Gender gender, String nationality, String numIdentityCard, PaperType paperType) {
        super(firstName, lastName, birthdate, gender, nationality);
        this.numIdentityCard = numIdentityCard;
        this.paperType = paperType;
    }

    public Passenger addBookings(Booking... bookings) {
        this.bookings.addAll(List.of(bookings));
        return this;
    }


    public PaperType getPaperType() {
        return paperType;
    }

    public void setPaperType(PaperType paperType) {
        this.paperType = paperType;
    }

    public String getNumIdentityCard() {
        return numIdentityCard;
    }

    public void setNumIdentityCard(String numIdentityCard) {
        this.numIdentityCard = numIdentityCard;
    }
}