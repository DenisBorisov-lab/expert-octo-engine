package com.example.expertoctoengine.service;

import com.example.expertoctoengine.model.Person;
import com.example.expertoctoengine.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void savePerson(Person person) {
        personRepository.save(person);
    }

    @Override
    public Person getPersonById(UUID uuid) {
        return personRepository.findPersonById(uuid);
    }

    @Override
    public Person getPersonByName(String name) {
        return personRepository.findPersonByName(name);
    }

    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public void removePerson(Person person) {
        if (personRepository.existsById(person.getId())) {
            personRepository.delete(person);
        } else {
            log.error(String.format("Person with id: %s was not found!", person.getId()));
        }
    }

    @Override
    public void changeNameById(UUID id, String name) {
        personRepository.updateNameById(id, name);
    }

    @Override
    public Person register(Person person) {
        Person data = personRepository.findPersonByName(person.getName());
        if (data == null){
            return personRepository.save(person);
        }
        return null;
    }
}
