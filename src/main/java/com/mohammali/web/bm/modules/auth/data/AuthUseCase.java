package com.mohammali.web.bm.modules.auth.data;

import com.mohammali.web.bm.common.exceptions.WrongCredentialsException;

import javax.servlet.http.HttpServletRequest;

public interface AuthUseCase {

    void login(HttpServletRequest request, String username, String password) throws WrongCredentialsException;

    void logout(HttpServletRequest request);
}
