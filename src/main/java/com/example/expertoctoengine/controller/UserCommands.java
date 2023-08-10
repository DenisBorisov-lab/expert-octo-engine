package com.example.expertoctoengine.controller;

import static com.example.expertoctoengine.model.Commands.*;

import com.example.expertoctoengine.model.Password;
import com.example.expertoctoengine.model.Person;
import com.example.expertoctoengine.service.PasswordService;
import com.example.expertoctoengine.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;


@ShellComponent
public class UserCommands {

    private final PersonService personService;
    private final PasswordService passwordService;
    private Person signedAccount;

    @Autowired
    public UserCommands(PersonService personService, PasswordService passwordService) {
        this.personService = personService;
        this.passwordService = passwordService;
    }

    @ShellMethod(key = "register", value = "Registering a new account if doest exists. Example: -r <username>")
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

    @ShellMethod(key = "login", value = "Enter account. Example: login <username>")
    public String login(@ShellOption String name){
        if (signedAccount == null){
            this.signedAccount = personService.getPersonByName(name);
            return this.signedAccount != null ? String.format("Welcome %s, you successfully logged in!", name) : ACCOUNT_NOT_FOUND.getText();
        }else{
            return LOGIN_ERROR.getText();
        }

    }

    @ShellMethod(key = "logout", value = "Leaving account. Example: logout")
    public String logout(){
        if (signedAccount == null){
            return LOGOUT_ERROR.getText();
        }else{
            signedAccount = null;
            return SUCCESSFUL_LOGOUT.getText();
        }
    }

    @ShellMethod(key = "save", value = "Saving password")
    public String savePassword(@ShellOption(arity = 3) String[] args){
        if(signedAccount != null){
            Password password = Password.builder().service(args[0]).login(args[1]).password(args[2]).person(signedAccount).build();
            passwordService.savePassword(password);
            return "Password was saved\n" +
                    "service: " + password.getService() + "\n"+
                    "login: " + password.getLogin() + "\n"+
                    "password: " + password.getPassword();

        }else{
            return ACCOUNT_NOT_FOUND.getText();
        }

    }

}
