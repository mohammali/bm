package com.mohammali.web.bm.modules.auth.data;

import com.mohammali.web.bm.common.exceptions.UserLoggedInException;
import com.mohammali.web.bm.common.exceptions.WrongCredentialsException;
import com.mohammali.web.bm.helper.LocalTest;
import com.mohammali.web.bm.modules.auth.model.UserAuthentication;
import com.mohammali.web.bm.modules.sessions.SessionManager;
import com.mohammali.web.bm.modules.user.data.UserUseCase;
import com.mohammali.web.bm.modules.user.model.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Optional;

@LocalTest
class AuthUseCaseTest {

    private final String username = "test";
    private final String password = "test";
    @MockBean
    private UserUseCase userUseCase;
    @Autowired
    private AuthUseCase testSubject;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SessionManager sessionManager;
    private UserEntity user;

    @BeforeEach
    void init() {
        user = new UserEntity(
            1L,
            "test",
            username,
            passwordEncoder.encode(password),
            Collections.singletonList("TEST")
        );
        Mockito.when(userUseCase.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
    }

    @Test
    void whenLoginCallWithValidCredentialsThenLogin() throws WrongCredentialsException {
        /*Given*/
        var mockHttpServletRequest = createHttpServletRequest();

        /*When*/
        testSubject.login(mockHttpServletRequest, username, password);

        /*Then*/
        var session = sessionManager.findByUsername(username);
        Assertions.assertNotNull(session);

        /*Clear*/
        session.invalidate();
    }

    @Test
    void whenLoginCallWithInvalidCredentialsThenThrowWrongCredentialsException() {
        var mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(mockHttpServletRequest.getSession(false)).thenReturn(null);
        Assertions.assertThrows(WrongCredentialsException.class, () -> {
            testSubject.login(mockHttpServletRequest, "", "");
        });

        /*clear*/
        sessionManager.invalidate();
    }

    @Test
    void whenLoginCallWithValidCredentialsTwiceThenThrowUserLoggedInException() throws WrongCredentialsException {
        /*Given*/
        var mockHttpServletRequest = createHttpServletRequest();
        testSubject.login(mockHttpServletRequest, username, password);

        Assertions.assertThrows(UserLoggedInException.class, () -> {
            testSubject.login(mockHttpServletRequest, username, password);
        });

        /*clear*/
        sessionManager.invalidate();
    }

    @Test
    void whenLogoutCallThenLogout() throws WrongCredentialsException {
        /*Given*/
        var mockHttpServletRequest = createHttpServletRequest();

        /*When*/
        testSubject.login(mockHttpServletRequest, username, password);
        var loggedSession = sessionManager.findByUsername(username);
        var mockLoggedInHttpServletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(mockLoggedInHttpServletRequest.getSession(false)).thenReturn(loggedSession);
        testSubject.logout(mockLoggedInHttpServletRequest);

        /*Then*/
        var session = sessionManager.findByUsername(username);
        Assertions.assertNull(session);
    }

    private HttpServletRequest createHttpServletRequest() {
        var mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(mockHttpServletRequest.getSession(false)).thenReturn(null);
        var mockHttpSession = Mockito.mock(HttpSession.class);
        Mockito.when(mockHttpSession.getId()).thenReturn("1");
        var auth = new UserAuthentication(
            user.getId(),
            user.getName(),
            user.getUsername(),
            user.getAuthority()
        );
        Mockito.when(mockHttpSession.getAttribute(SessionManager.SECURITY_CONTEXT_KEY)).thenReturn(auth);
        Mockito.when(mockHttpServletRequest.getSession(true)).thenReturn(mockHttpSession);
        return mockHttpServletRequest;
    }
}
