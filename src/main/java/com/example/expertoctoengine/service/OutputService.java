package com.example.expertoctoengine.service;

import com.example.expertoctoengine.model.Password;

import java.util.List;

public class OutputService {
    public static String outputSavedPassword(Password password) {
        return "Password was saved\n" + outputPassword(password);
    }

    public static String outputPassword(Password password) {
        return String.format("> id: %s\n> service: %s\n> login: %s\n> password: %s",
                password.getId(),
                password.getService(),
                password.getLogin(),
                password.getPassword());
    }

    public static String outputListPasswords(List<Password> passwords) {
        StringBuilder builder = new StringBuilder();
        builder.append("> id | service | login | password\n");
        for (Password password : passwords) {
            builder.append(String.format("> %s %s %s %s\n",
                    password.getId(),
                    password.getService(),
                    password.getLogin(),
                    password.getPassword()));
        }

        return builder.substring(0, builder.length() - 1);
    }
}
