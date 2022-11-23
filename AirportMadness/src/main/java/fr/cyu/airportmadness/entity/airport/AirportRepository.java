package fr.cyu.airportmadness.entity.airport;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface AirportRepository extends PagingAndSortingRepository<Airport, Long> {
}