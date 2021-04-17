package com.mohammali.web.bm.common.exceptions;

public class WrongCredentialsException extends Exception {

    public WrongCredentialsException() {
        super("wrong credentials");
    }
}
