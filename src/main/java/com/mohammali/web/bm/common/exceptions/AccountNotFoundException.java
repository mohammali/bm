package com.mohammali.web.bm.common.exceptions;

public class AccountNotFoundException extends NotFoundException {

    public AccountNotFoundException(String accountNumber) {
        super("The account number: " + accountNumber + " not found");
    }

    public AccountNotFoundException(Long id) {
        super("The account id: " + id + " not found");
    }
}
