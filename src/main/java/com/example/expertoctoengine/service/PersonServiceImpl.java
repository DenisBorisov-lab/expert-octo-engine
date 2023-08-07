package com.example.expertoctoengine.service;

import com.example.expertoctoengine.model.Person;
import com.example.expertoctoengine.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonServiceImpl implements PersonService{

    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository){
        this.personRepository = personRepository;
    }
    @Override
    @Transactional
    public void savePerson(Person person) {
        personRepository.save(person);
    }
}
