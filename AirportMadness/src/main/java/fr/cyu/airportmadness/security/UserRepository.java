package fr.cyu.airportmadness.security;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByUsernameLike(@NonNull String username);

    User findByRoleLike(String role);


}