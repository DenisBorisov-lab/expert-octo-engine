package com.example.expertoctoengine.service;

import com.example.expertoctoengine.model.Password;
import com.example.expertoctoengine.repository.PasswordRepository;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService {

    private final PasswordRepository passwordRepository;

    public PasswordServiceImpl(PasswordRepository passwordRepository) {
        this.passwordRepository = passwordRepository;
    }

    @Override
    public void savePassword(Password password) {
        passwordRepository.save(password);
    }
}
