package com.example.expertoctoengine.service;

import com.example.expertoctoengine.model.Person;

import java.util.List;
import java.util.UUID;

public interface PersonService {
    void savePerson(Person person);

    Person getPersonById(UUID uuid);

    List<Person> getPersonsByName(String name);

    List<Person> getAllPersons();

    void removePerson(Person person);

    void changeNameById(UUID id, String name);
}
