package fr.cyu.airportmadness.security;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsernameLike(@NonNull String username);

    User findByUsername(@NonNull String username);



    User findByRoleLike(String role);


}