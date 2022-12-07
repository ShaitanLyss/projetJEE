package fr.cyu.airportmadness.entity.aircraft;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AircraftRepository extends CrudRepository<Aircraft, Long>, PagingAndSortingRepository<Aircraft, Long> {
    List<Aircraft> findByRegistration(@Param("registration") String registration);
}
