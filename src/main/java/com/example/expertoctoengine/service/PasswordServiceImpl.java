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
    public void savePassword(Password password) {
        passwordRepository.save(password);
    }

    @Override
    public void removePassword(Password password) {
        if (passwordRepository.existsById(password.getId())){
            passwordRepository.delete(password);
        }else{
            log.error(String.format("Password with id: %s was not found!", password.getId()));
        }
    }

    @Override
    public Password getPasswordById(Long id) {
        return passwordRepository.findPasswordById(id);
    }

    @Override
    public List<Password> getPasswordsByPersonId(UUID id) {
        return passwordRepository.findPasswordsByPersonId(id);
    }

    @Override
    public List<Password> getPasswordsByPersonIdAndService(UUID uuid, String service) {
        return passwordRepository.findPasswordsByPersonIdAndService(uuid, service);
    }

    @Override
    public List<Password> getPasswordsByPersonIdAndServiceAndLogin(UUID uuid, String service, String login) {
        return passwordRepository.findPasswordsByPersonIdAndServiceAndLogin(uuid, service, login);
    }

    @Override
    public void changePasswordByPersonIdAndServiceAndLogin(Person person, String service, String login, String newPassword) {
        passwordRepository.updatePasswordByPersonIdAndServiceAndLogin(person, service, login, newPassword);
    }

    @Override
    public void changeLoginByPersonIdAndServiceAndPassword(Person person, String service, String password, String newLogin) {
        passwordRepository.updateLoginByPersonIdAndServiceAndPassword(person, service, password, newLogin);
    }

    @Override
    public void changeServiceByPeronIdAndLoginAndPassword(Person person, String login, String password, String newServiceName) {
        passwordRepository.updateServiceByPersonIdAndLoginAndPassword(person, login, password, newServiceName);
    }
}
