package fr.cyu.airportmadness.entity.airline;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface AirlineRepository extends PagingAndSortingRepository<Airline, Long> {
}