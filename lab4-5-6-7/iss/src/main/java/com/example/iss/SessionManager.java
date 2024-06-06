package com.example.iss;

import domain.UserSession;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static Map<String, UserSession> sessions = new HashMap<>();

    public static void addSession(String sessionId, UserSession userSession) {
        sessions.put(sessionId, userSession);
    }

    public static UserSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public static void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }
}

