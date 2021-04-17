package com.mohammali.web.bm.modules.auth.web;

import com.mohammali.web.bm.common.exceptions.WrongCredentialsException;
import com.mohammali.web.bm.modules.auth.data.AuthUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthControllerTest {

    @LocalServerPort
    private Integer port;

    @MockBean
    private AuthUseCase authUseCase;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void whenLoginThenReturn200HttpStatus() throws WrongCredentialsException {
        Mockito.doNothing().when(authUseCase).login(Mockito.any(), Mockito.any(), Mockito.any());

        var url = "http://localhost:" + port + "/api.auth/login";
        var values = new LinkedMultiValueMap<String, String>();
        values.add("username", "test");
        values.add("password", "test");
        var requestEntity = new HttpEntity<MultiValueMap<String, String>>(values, null);
        var response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    void whenLoginExceptionOccurThenReturn403HttpStatus() throws WrongCredentialsException {
        Mockito.doThrow(WrongCredentialsException.class).when(authUseCase).login(Mockito.any(), Mockito.any(), Mockito.any());

        var url = "http://localhost:" + port + "/api.auth/login";
        var values = new LinkedMultiValueMap<String, String>();
        values.add("username", "test");
        values.add("password", "test");
        var requestEntity = new HttpEntity<MultiValueMap<String, String>>(values, null);
        var response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCodeValue());
    }

    @Test
    void whenLogoutThenReturn200HttpStatus() {
        Mockito.doNothing().when(authUseCase).logout(Mockito.any());
        var url = "http://localhost:" + port + "/api.auth/logout";
        var response = restTemplate.exchange(url, HttpMethod.POST, HttpEntity.EMPTY, String.class);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

}
