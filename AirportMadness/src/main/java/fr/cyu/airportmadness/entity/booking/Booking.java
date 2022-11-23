package fr.cyu.airportmadness.entity.booking;

import fr.cyu.airportmadness.entity.person.customer.Customer;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "num_luggages", nullable = false)
    private Integer numLuggages;

    @Column(name = "price", nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getNumLuggages() {
        return numLuggages;
    }

    public void setNumLuggages(Integer numLuggages) {
        this.numLuggages = numLuggages;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}