package com.example.expertoctoengine.service;

import com.example.expertoctoengine.model.Password;

public class OutputService {
    public static String outputSavedPassword(Password password){
        return new StringBuilder("Password was saved\n").append(outputPassword(password)).toString();
    }

    public static String outputPassword(Password password){
        return String.format("> id: %s\n> service: %s\n> login: %s\n> password: %s",
                password.getId(),
                password.getService(),
                password.getLogin(),
                password.getPassword());
    }
}
