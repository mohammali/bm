package com.mohammali.web.bm.common.exceptions;

public class UserLoggedInException extends RuntimeException {

    public UserLoggedInException() {
        super("This user already logged in");
    }
}
