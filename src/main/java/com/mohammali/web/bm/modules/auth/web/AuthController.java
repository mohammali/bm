package com.mohammali.web.bm.modules.auth.web;

import com.mohammali.web.bm.common.exceptions.UserLoggedInException;
import com.mohammali.web.bm.common.exceptions.WrongCredentialsException;
import com.mohammali.web.bm.modules.auth.data.AuthUseCase;
import com.mohammali.web.bm.modules.auth.model.UsernamePasswordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api.auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping("login")
    public ResponseEntity<String> login(
        HttpServletRequest request,
        @RequestBody UsernamePasswordDto body
    ) {
        try {
            authUseCase.login(request, body.getUsername(), body.getPassword());
            return ResponseEntity.ok("SUCCESS");
        } catch (WrongCredentialsException | UserLoggedInException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("logout")
    public ResponseEntity<String> logout(
        HttpServletRequest request
    ) {
        authUseCase.logout(request);
        return ResponseEntity.ok("SUCCESS");
    }
}
