package com.mohammali.web.bm.modules.sessions;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mohammali.web.bm.common.exceptions.NotFoundException;
import com.mohammali.web.bm.modules.auth.model.UserAuthentication;
import org.springframework.lang.NonNull;

import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.util.HashMap;

final class SessionManagerImpl implements SessionManager {

    private final LoadingCache<String, HttpSession> cache;

    public SessionManagerImpl(Duration duration) {
        cache = CacheBuilder.newBuilder()
            .expireAfterWrite(duration)
            .build(new CacheLoader<>() {
                @Override
                public HttpSession load(@NonNull String s) throws Exception {
                    throw new NotFoundException("not found " + s);
                }
            });
    }

    @Override
    public void addSession(HttpSession session) {
        cache.put(session.getId(), session);
    }

    @Override
    public HttpSession findByUsername(String username) {
        var clone = new HashMap<>(cache.asMap());
        for (HttpSession session : clone.values()) {
            var securityContext = session.getAttribute(SECURITY_CONTEXT_KEY);
            if (securityContext == null) {
                continue;
            }

            var auth = (UserAuthentication) securityContext;
            if (auth.getUsername().equalsIgnoreCase(username)) {
                return session;
            }
        }
        return null;
    }

    @Override
    public boolean deleteById(String id) {
        try {
            cache.invalidate(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public HttpSession findById(String id) {
        try {
            return cache.get(id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public long invalidate() {
        var size = cache.size();
        cache.invalidateAll();
        return size;
    }
}
