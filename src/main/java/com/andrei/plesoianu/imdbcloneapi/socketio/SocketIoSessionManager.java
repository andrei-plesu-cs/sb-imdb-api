package com.andrei.plesoianu.imdbcloneapi.socketio;

import com.corundumstudio.socketio.SocketIOClient;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SocketIoSessionManager {
    private final Map<Long, SocketIOClient> sessions = new HashMap<>();
    private final Map<SocketIOClient, Long> reverseSessions = new HashMap<>();

    public synchronized void addSession(Long userId, SocketIOClient client) {
        sessions.put(userId, client);
        reverseSessions.put(client, userId);
    }

    public synchronized void removeSessionByUserId(Long userId) {
        var session = sessions.remove(userId);
        if (session != null) {
            reverseSessions.remove(session);
        }
    }

    public synchronized void removeSessionBySocket(SocketIOClient client) {
        var userId = reverseSessions.remove(client);
        if (userId != null) {
            sessions.remove(userId);
        }
    }

    public SocketIOClient getSession(Long userId) {
        return sessions.get(userId);
    }
}
