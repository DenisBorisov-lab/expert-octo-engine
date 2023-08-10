package com.example.expertoctoengine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum Commands {
    ACCOUNT_ALREADY_EXISTS("This account is already exists"),
    ACCOUNT_SUCCESSFULLY_REGISTERED("Account was successfully registered, Welcome!"),
    ACCOUNT_NOT_FOUND("Account was not found!"),
    SUCCESSFUL_LOGOUT("You are successfully logout"),
    LOGOUT_ERROR("You have already logged out"),
    LOGIN_ERROR("Please, logout firstly");

    private final String text;


}
