package fr.cyu.airportmadness.entity.booking;

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

    @Column(name = "num_luggages", nullable = false)
    private Integer numLuggages;

    @Column(name = "price", nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

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