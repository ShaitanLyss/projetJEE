package fr.cyu.airportmadness.entity.person.passenger.customer;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {
}