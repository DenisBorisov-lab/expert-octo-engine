package com.example.expertoctoengine;

import com.example.expertoctoengine.model.Password;
import com.example.expertoctoengine.model.Person;
import com.example.expertoctoengine.service.PasswordService;
import com.example.expertoctoengine.service.PersonService;
import com.example.expertoctoengine.service.PersonServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.UUID;

@SpringBootApplication
public class ExpertOctoEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpertOctoEngineApplication.class, args);

//        PersonService personService = context.getBean(PersonServiceImpl.class);
//        PasswordService passwordService = context.getBean(PasswordService.class);
//
//        Person person = Person.builder().name("Alex").build();
//        Password password = Password.builder().person(person).service("Spotify").login("dickhead@gmail.com").password("MikeOxLong228").build();
//
//        personService.savePerson(person);
//        passwordService.savePassword(password);
//
//        Person object = personService.getAllPersons().get(0);
//
//        passwordService.changeServiceByPeronIdAndLoginAndPassword(object, "dickhead@gmail.com", "MikeOxLong228", "Yandex");
//        System.out.println(passwordService.getPasswordsByPersonIdAndServiceAndLogin(object.getId(), "Spotify", "dickhead@gmail.com"));
    }

}
