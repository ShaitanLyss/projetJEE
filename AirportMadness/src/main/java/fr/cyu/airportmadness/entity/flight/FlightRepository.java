package fr.cyu.airportmadness.entity.flight;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface FlightRepository extends PagingAndSortingRepository<Flight, Long> {
}