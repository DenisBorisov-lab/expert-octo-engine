package com.example.expertoctoengine.controller;

import com.example.expertoctoengine.model.Password;
import com.example.expertoctoengine.model.Person;
import com.example.expertoctoengine.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

import static com.example.expertoctoengine.model.Commands.*;


@ShellComponent
public class UserCommands {

    private final PersonService personService;
    private final PasswordService passwordService;
    private Person signedAccount;

    @Autowired
    public UserCommands(PersonServiceImpl personService, PasswordServiceImpl passwordService) {
        this.personService = personService;
        this.passwordService = passwordService;
    }

    @ShellMethod(key = "register", value = REGISTER_VALUE)
    public String register(@ShellOption String name) {
        Person newPerson = Person.builder()
                .name(name)
                .build();
        if (!isAuthorized()) {
            this.signedAccount = personService.register(newPerson);
            return isAuthorized() ? ACCOUNT_SUCCESSFULLY_REGISTERED : ACCOUNT_ALREADY_EXISTS;
        } else {
            return LOGIN_ERROR;
        }
    }

    @ShellMethod(key = "login", value = LOGIN_VALUE)
    public String login(@ShellOption String name) {
        if (!isAuthorized()) {
            this.signedAccount = personService.getPersonByName(name);
            return isAuthorized() ? String.format("Welcome %s, you successfully logged in!", name) : ACCOUNT_NOT_FOUND;
        } else {
            return LOGIN_ERROR;
        }
    }

    @ShellMethod(key = "logout", value = LOGOUT_VALUE)
    public String logout() {
        if (!isAuthorized()) {
            return LOGOUT_ERROR;
        } else {
            this.signedAccount = null;
            return SUCCESSFUL_LOGOUT;
        }
    }

    @ShellMethod(key = "save", value = "Saving password. Example: save <service> <login> <password>")
    public String savePassword(@ShellOption(arity = 3) String[] args) {
        if (isAuthorized()) {
            Password password = Password.builder().service(args[0]).login(args[1]).password(args[2]).person(signedAccount).build();
            Password data = passwordService.savePassword(password);
            if (data != null) {
                return OutputService.outputSavedPassword(password);
            } else {
                return PASSWORD_ALREADY_EXIST;
            }
        } else {
            return LOGIN_FIRSTLY;
        }
    }

    @ShellMethod(key = "password -id", value = "Getting password by id. Example: password -id <id number>")
    public String getPasswordById(@ShellOption Long id) {
        if (isAuthorized()) {
            Password password = passwordService.getPasswordById(id);
            if (password != null) {
                return OutputService.outputPassword(password);
            } else {
                return NO_PASSWORD_WITH_SUCH_ID;
            }
        } else {
            return LOGIN_FIRSTLY;
        }
    }

    @ShellMethod(key = "password -s", value = "Getting passwords by service. Example: passwords -s <service>")
    public String getPasswordsByService(@ShellOption String service) {
        if (isAuthorized()) {
            List<Password> passwords = passwordService.getPasswordsByPersonIdAndService(
                    signedAccount.getId(),
                    service);
            return passwords != null ? OutputService.outputListPasswords(passwords) : NO_PASSWORDS;
        } else {
            return LOGIN_FIRSTLY;
        }
    }

    @ShellMethod(key = "password", value = "Getting password by service and login. Example: password <service> <login>")
    public String getPasswordByServiceAndLogin(@ShellOption(arity = 2) String[] args){
        if(isAuthorized()){
            String serviceName = args[0];
            String login = args[1];
            Password password = passwordService.getPasswordByPersonIdAndServiceAndLogin(signedAccount.getId(), serviceName, login);
            return password != null ? OutputService.outputPassword(password) : NO_SUCH_PASSWORD;

        }else{
            return ACCOUNT_NOT_FOUND;
        }

    }

    @ShellMethod(key = "password -a", value = "Getting all passwords by user id. Example: password -a")
    public String getAllPasswords() {
        if (isAuthorized()) {
            List<Password> passwords = passwordService.getPasswordsByPersonId(signedAccount.getId());
            return passwords != null ? OutputService.outputListPasswords(passwords) : NO_PASSWORDS;
        } else {
            return LOGIN_FIRSTLY;
        }
    }

    @ShellMethod(key = "delete -id", value = "Removal password by id. Example: delete -id <id number>")
    public String delete(@ShellOption Long id) {
        if (isAuthorized()) {
            Password passwordById = passwordService.getPasswordById(id);
            passwordService.removePassword(passwordById);
            return passwordById != null ? PASSWORD_DELETED_SUCCESSFULLY : NO_PASSWORD_WITH_SUCH_ID;
        } else {
            return ACCOUNT_NOT_FOUND;
        }
    }

    @ShellMethod(key = "delete -a", value = "Removal all passwords for this account. Example: delete -a")
    public String deleteAll() {
        if (isAuthorized()) {
            passwordService.removeAllByPersonId(signedAccount.getId());
            return PASSWORDS_DELETED_SUCCESSFULLY;
        } else {
            return ACCOUNT_NOT_FOUND;
        }
    }

    @ShellMethod(key = "delete -s", value = "Removal all passwords for current service. Example: delete -s <service>")
    public String deleteService(@ShellOption String name) {
        if(isAuthorized()){
            passwordService.removeALLByServiceName(signedAccount.getId(), name);
            return PASSWORDS_DELETED_SUCCESSFULLY;
        }else{
            return ACCOUNT_NOT_FOUND;
        }
    }

    @ShellMethod(key = "delete account", value = "Removal account. You need to be logged in. Example: delete -acc")
    public String deleteAccount(){
        if (isAuthorized()){
            personService.removePerson(signedAccount);
            this.signedAccount = null;
            return ACCOUNT_SUCCESSFULLY_REMOVED;
        }else{
            return ACCOUNT_NOT_FOUND;
        }
    }

    private boolean isAuthorized() {
        return this.signedAccount != null;
    }

}
