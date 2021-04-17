package com.mohammali.web.bm.modules.auth.model;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserAuthentication implements Authentication {

    private final Long id;
    private final String name;
    private final String username;
    private final List<String> authority;
    private boolean authenticated;

    public UserAuthentication(
        Long id,
        String name,
        String username,
        List<String> authority
    ) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.authority = authority;
        this.authenticated = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authority.stream().map(auth -> (GrantedAuthority) () -> "ROLE_" + auth).collect(Collectors.toList());
    }

    @Override
    public Object getCredentials() {
        return this;
    }

    @Override
    public Object getDetails() {
        return this;
    }

    @Override
    public Object getPrincipal() {
        return this;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        this.authenticated = authenticated;
    }
}