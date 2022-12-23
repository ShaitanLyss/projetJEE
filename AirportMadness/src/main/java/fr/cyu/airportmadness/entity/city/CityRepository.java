package fr.cyu.airportmadness.entity.city;

import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, Long> {
    City findByName(String name);
}