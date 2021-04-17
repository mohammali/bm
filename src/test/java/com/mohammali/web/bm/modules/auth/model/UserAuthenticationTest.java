package com.mohammali.web.bm.modules.auth.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

class UserAuthenticationTest {

    private final UserAuthentication authentication = new UserAuthentication(
        1L,
        "test",
        "test",
        Collections.singletonList("TEST")
    );

    @Test
    void checkAuthenticationImplementation() {
        Assertions.assertTrue(authentication.getAuthorities().stream().allMatch(grantedAuthority -> grantedAuthority.getAuthority().startsWith("ROLE")));

        Assertions.assertEquals(authentication, authentication.getCredentials());
        Assertions.assertEquals(authentication, authentication.getDetails());
        Assertions.assertEquals(authentication, authentication.getPrincipal());

        Assertions.assertTrue(authentication.isAuthenticated());
        authentication.setAuthenticated(false);
        Assertions.assertFalse(authentication.isAuthenticated());
    }

    @Test
    void checkGetters() {
        Assertions.assertEquals(1L, authentication.getId());
        Assertions.assertEquals("test", authentication.getName());
        Assertions.assertEquals("test", authentication.getUsername());
    }
}
