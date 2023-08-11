package com.example.expertoctoengine.controller;

import com.example.expertoctoengine.model.Password;
import com.example.expertoctoengine.model.Person;
import com.example.expertoctoengine.service.OutputService;
import com.example.expertoctoengine.service.PasswordService;
import com.example.expertoctoengine.service.PersonService;
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
    public UserCommands(PersonService personService, PasswordService passwordService) {
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

    @ShellMethod(key = "password -id", value = "Getting password. Example: password -id <id number>")
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

    // FIXME: 11.08.2023 поправить вывод
    // FIXME: 11.08.2023 проверить количество паролей в списке и проверить на null
    // FIXME: 11.08.2023 Написать Описание
    @ShellMethod(key = "password -s", value = "Описание")
    public String getPasswordsByService(@ShellOption String service) {
        if (signedAccount != null) {
            List<Password> passwordsByPersonIdAndService = passwordService.getPasswordsByPersonIdAndService(signedAccount.getId(), service);
            return passwordsByPersonIdAndService.toString();
        } else {
            return ACCOUNT_NOT_FOUND;
        }
    }

    // FIXME: 11.08.2023 поправить вывод
    // FIXME: 11.08.2023 проверить количество паролей в списке и проверить на null
    // FIXME: 11.08.2023 Написать Описание
    @ShellMethod(key = "password -a")
    public String getAllPasswords() {
        if (signedAccount != null) {
            List<Password> passwords = passwordService.getPasswordsByPersonId(signedAccount.getId());
            return passwords.toString();
        } else {
            return ACCOUNT_NOT_FOUND;
        }
    }

    // FIXME: 11.08.2023 Предыдущие проблемы но ещё проверки на существование пароля
    @ShellMethod(key = "delete -id", value = "Описание")
    public String delete(@ShellOption Long id) {
        if (signedAccount != null) {
            Password passwordById = passwordService.getPasswordById(id);
            passwordService.removePassword(passwordById);
            return "Пароль удалён!";
        } else {
            return ACCOUNT_NOT_FOUND;
        }
    }

    @ShellMethod(key = "delete -a")
    public String deleteAll() {
        return null;
    }

    public String deleteService() {
        return null;
    }

    private boolean isAuthorized() {
        return this.signedAccount != null;
    }

}
