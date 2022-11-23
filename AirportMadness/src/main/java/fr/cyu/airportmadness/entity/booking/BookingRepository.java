package fr.cyu.airportmadness.entity.booking;

import org.springframework.data.repository.PagingAndSortingRepository;


public interface BookingRepository extends PagingAndSortingRepository<Booking, Long> {
}