package fr.cyu.airportmadness.entity.person.passenger;

import fr.cyu.airportmadness.entity.person.Person;

import javax.persistence.*;

@Entity
public class Passenger extends Person {
    @Column(name = "num_identity_card", nullable = false)
    private String numIdentityCard;

    @Enumerated(EnumType.STRING)
    @Column(name = "paper_type")
    private PaperType paperType;

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