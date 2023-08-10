package com.example.expertoctoengine.controller;

import static com.example.expertoctoengine.model.Commands.*;
import com.example.expertoctoengine.model.Person;
import com.example.expertoctoengine.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;


@ShellComponent
public class UserCommands {

    private final PersonService personService;
    private Person signedAccount;

    @Autowired
    public UserCommands(PersonService personService) {
        this.personService = personService;
    }

    @ShellMethod(key = "-r", value = "Registering a new account if doest exists. Example: -r <username>")
    public String register(@ShellOption String name){
        Person newPerson = Person.builder()
                .name(name)
                .build();
        if (signedAccount == null){
            this.signedAccount = personService.register(newPerson);
            return this.signedAccount != null ? ACCOUNT_SUCCESSFULLY_REGISTERED.getText() : ACCOUNT_ALREADY_EXISTS.getText();
        }else{
            return LOGIN_ERROR.getText();
        }

    }

    @ShellMethod(key = "login", value = "Entering account. Example: -login <username>")
    public String login(@ShellOption String name){
        if (signedAccount == null){
            this.signedAccount = personService.getPersonByName(name);
            return this.signedAccount != null ? String.format("Welcome %s, you successfully logged in!", name) : ACCOUNT_NOT_FOUND.getText();
        }else{
            return LOGIN_ERROR.getText();
        }

    }

    @ShellMethod(key = "logout", value = "Leaving account. Example: -logout")
    public String logout(){
        if (signedAccount == null){
            return LOGOUT_ERROR.getText();
        }else{
            signedAccount = null;
            return SUCCESSFUL_LOGOUT.getText();
        }
    }

}
