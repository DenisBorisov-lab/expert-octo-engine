package com.example.expertoctoengine.service;

import com.example.expertoctoengine.model.Password;
import com.example.expertoctoengine.model.Person;
import com.example.expertoctoengine.repository.PasswordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class PasswordServiceImpl implements PasswordService {

    private final PasswordRepository passwordRepository;

    public PasswordServiceImpl(PasswordRepository passwordRepository) {
        this.passwordRepository = passwordRepository;
    }

    @Override
    public Password savePassword(Password password) {
        Password data = passwordRepository.findPasswordByPersonIdAndServiceAndLogin(
                password.getPerson().getId(),
                password.getService(),
                password.getLogin());

        return data == null ? passwordRepository.save(password) : null;
    }

    @Override
    public void removePassword(Password password) {
        if (passwordRepository.existsById(password.getId())) {
            passwordRepository.delete(password);
        } else {
            log.error(String.format("Password with id: %s was not found!", password.getId()));
        }
    }

    @Override
    public void removeAllByPersonId(UUID id) {
        passwordRepository.removeAllByPersonId(id);
    }

    @Override
    public void removeALLByServiceName(UUID id, String serviceName) {
        passwordRepository.removeALLByPersonIdAndService(id, serviceName);
    }

    @Override
    public Password getPasswordById(Long id) {
        return passwordRepository.findPasswordById(id);
    }

    @Override
    public List<Password> getPasswordsByPersonId(UUID id) {
        List<Password> passwords = passwordRepository.findPasswordsByPersonId(id);
        return passwords.isEmpty() ? null : passwords;
    }

    @Override
    public List<Password> getPasswordsByPersonIdAndService(UUID uuid, String service) {
        List<Password> passwords = passwordRepository.findPasswordsByPersonIdAndService(uuid, service);
        return passwords.isEmpty() ? null : passwords;
    }

    @Override
    public Password getPasswordByPersonIdAndServiceAndLogin(UUID uuid, String service, String login) {
        return passwordRepository.findPasswordByPersonIdAndServiceAndLogin(uuid, service, login);
    }

    @Override
    public boolean changePasswordByPersonIdAndServiceAndLogin(Person person, String service, String login, String newPassword) {
        passwordRepository.updatePasswordByPersonIdAndServiceAndLogin(person, service, login, newPassword);
        return passwordRepository.findPasswordByPersonIdAndServiceAndLogin(person.getId(), service, login) != null;
    }

    @Override
    public boolean changeLoginByPersonIdAndServiceAndPassword(Person person, String service, String password, String newLogin) {
        passwordRepository.updateLoginByPersonIdAndServiceAndPassword(person, service, password, newLogin);
        return passwordRepository.findPasswordByPersonIdAndServiceAndLogin(person.getId(), service, newLogin) != null;
    }

    @Override
    public void changeServiceByPersonId(Person person, String newService, String oldService) {
        passwordRepository.updateServiceByPersonId(person, newService, oldService);
    }

    @Override
    public boolean changeServiceByPasswordId(Long id, String serviceName) {
        passwordRepository.changeServiceByPasswordId(id, serviceName);
        return passwordRepository.existsById(id);
    }

    @Override
    public boolean changePasswordById(Long id, String password) {
        passwordRepository.changePasswordByPasswordId(id, password);
        return passwordRepository.existsById(id);
    }

    @Override
    public boolean changeLoginById(Long id, String login) {
        passwordRepository.changeLoginById(id, login);
        return passwordRepository.existsById(id);
    }
}
