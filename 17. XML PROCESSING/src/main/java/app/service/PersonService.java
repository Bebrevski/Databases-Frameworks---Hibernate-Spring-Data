package app.service;

import app.domain.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    void create(Person person);

    Optional<Person> findById(long id);

    List<Person> findByCountry(String country);
}
