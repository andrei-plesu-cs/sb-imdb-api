package com.andrei.plesoianu.imdbcloneapi.socketio;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class SocketModule {
    private final SocketIOServer socketIOServer;

    public SocketModule(@NonNull SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
        socketIOServer.addConnectListener(onConnected());
        socketIOServer.addDisconnectListener(onDisconnected());
    }

    private ConnectListener onConnected() {
        return (client) -> System.out.format("Socket ID[%s]  Connected to socket%n", client.getSessionId().toString());
    }

    private DisconnectListener onDisconnected() {
        return client -> System.out.format("Client[%s] - Disconnected from socket%n", client.getSessionId().toString());
    }
}
