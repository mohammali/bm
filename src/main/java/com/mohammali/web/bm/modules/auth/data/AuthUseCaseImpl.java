package com.mohammali.web.bm.modules.auth.data;

import com.mohammali.web.bm.common.exceptions.UserLoggedInException;
import com.mohammali.web.bm.common.exceptions.WrongCredentialsException;
import com.mohammali.web.bm.modules.auth.model.UserAuthentication;
import com.mohammali.web.bm.modules.sessions.SessionManager;
import com.mohammali.web.bm.modules.user.data.UserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Service
@RequiredArgsConstructor
final class AuthUseCaseImpl implements AuthUseCase {

    private final UserUseCase userUseCase;
    private final PasswordEncoder passwordEncoder;
    private final SessionManager sessionManager;

    @Override
    public void login(
        HttpServletRequest request,
        String username,
        String password
    ) throws WrongCredentialsException {
        var currentSession = getUserCurrentSession(request, username);
        if (currentSession != null) {
            throw new UserLoggedInException();
        }

        var userOptional = userUseCase.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new WrongCredentialsException();
        }

        var user = userOptional.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new WrongCredentialsException();
        }

        var session = request.getSession(true);
        session.setAttribute(
            SessionManager.SECURITY_CONTEXT_KEY,
            new UserAuthentication(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getAuthority()
            )
        );

        sessionManager.addSession(session);
        request.getSession(true);
    }

    @Override
    public void logout(HttpServletRequest request) {
        try {
            var session = request.getSession(false);
            if (session != null) {
                sessionManager.deleteById(session.getId());
            }
        } catch (Exception e) {
            log.warn("bad state of the session");
        }
    }

    private HttpSession getUserCurrentSession(HttpServletRequest request, String username) {
        var session = request.getSession(false);
        if (session == null) {
            return sessionManager.findByUsername(username);
        } else {
            return session;
        }
    }
}
