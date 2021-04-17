package com.mohammali.web.bm.config.security;

import com.mohammali.web.bm.modules.sessions.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
final class SessionFilter extends OncePerRequestFilter {

    private final SessionManager sessionManager;

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest httpServletRequest,
        @NonNull HttpServletResponse httpServletResponse,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        var session = httpServletRequest.getSession(false);
        if (session != null) {
            var currentActiveSession = sessionManager.findById(session.getId());
            if (currentActiveSession == null) {
                httpServletResponse.setStatus(440);
                return;
            }
            SecurityContextHolder.getContext().setAuthentication((Authentication) currentActiveSession.getAttribute(SessionManager.SECURITY_CONTEXT_KEY));
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
