package fr.cyu.airportmadness.entity.booking;

import org.springframework.data.repository.CrudRepository;


public interface BookingRepository extends CrudRepository<Booking, Long> {
}