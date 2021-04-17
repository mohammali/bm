package com.mohammali.web.bm.modules.sessions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class SessionManagerConfig {

    @Bean
    public SessionManager sessionManager() {
        return new SessionManagerImpl(Duration.ofMinutes(5));
    }
}
