package com.example.expertoctoengine;

import com.example.expertoctoengine.model.Person;
import com.example.expertoctoengine.service.PersonService;
import com.example.expertoctoengine.service.PersonServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ExpertOctoEngineApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ExpertOctoEngineApplication.class, args);
        PersonService service = context.getBean(PersonServiceImpl.class);
        Person person = Person.builder().name("Alex").build();
        service.savePerson(person);
    }

}
