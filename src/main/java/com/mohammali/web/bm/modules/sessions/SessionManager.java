package com.mohammali.web.bm.modules.sessions;

import javax.servlet.http.HttpSession;

public interface SessionManager {

    String SECURITY_CONTEXT_KEY = "SECURITY_CONTEXT";

    void addSession(HttpSession session);

    boolean deleteById(String id);

    HttpSession findById(String id);

    HttpSession findByUsername(String username);

    long invalidate();
}
