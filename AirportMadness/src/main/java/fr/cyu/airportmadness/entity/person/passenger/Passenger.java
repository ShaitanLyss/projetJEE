package fr.cyu.airportmadness.entity.person.passenger;

import fr.cyu.airportmadness.entity.booking.Booking;
import fr.cyu.airportmadness.entity.person.Person;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Passenger extends Person {
    @Column(name = "num_identity_card", nullable = false)
    private String numIdentityCard;

    @Enumerated(EnumType.STRING)
    @Column(name = "paper_type")
    private PaperType paperType;

    @ManyToMany
    @JoinTable(name = "null_bookings",
            joinColumns = @JoinColumn(name = "passenger_id"),
            inverseJoinColumns = @JoinColumn(name = "bookings_id"))
    private List<Booking> bookings = new ArrayList<>();

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
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