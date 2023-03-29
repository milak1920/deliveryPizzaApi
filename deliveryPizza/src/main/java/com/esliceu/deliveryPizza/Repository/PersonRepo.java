package com.esliceu.deliveryPizza.Repository;

import com.esliceu.deliveryPizza.Model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PersonRepo extends JpaRepository<Person, Integer> {
    Person findByEmail(String userName);

    Optional<Person> findUserByEmail(String email);
}
