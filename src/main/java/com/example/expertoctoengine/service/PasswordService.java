package com.example.expertoctoengine.service;

import com.example.expertoctoengine.model.Password;
import com.example.expertoctoengine.model.Person;

import java.util.List;
import java.util.UUID;

public interface PasswordService {
    Password savePassword(Password password);
    void removePassword(Password password);
    Password getPasswordById(Long id);
    List<Password> getPasswordsByPersonId(UUID id);
    List<Password> getPasswordsByPersonIdAndService(UUID uuid, String service);
    Password getPasswordByPersonIdAndServiceAndLogin(UUID uuid, String service, String login);
    void changePasswordByPersonIdAndServiceAndLogin(Person person, String service, String login, String newPassword);
    void changeLoginByPersonIdAndServiceAndPassword(Person person, String service, String password, String newLogin);
    void changeServiceByPeronIdAndLoginAndPassword(Person person, String login, String password, String newServiceName);


}
