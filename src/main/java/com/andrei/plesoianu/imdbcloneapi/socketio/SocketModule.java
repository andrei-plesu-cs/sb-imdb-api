package com.andrei.plesoianu.imdbcloneapi.socketio;

import com.andrei.plesoianu.imdbcloneapi.payloads.socketio.MessageDto;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import jakarta.annotation.PreDestroy;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class SocketModule {
    private final SocketIOServer socketIOServer;
    private final SocketIoSessionManager sessionManager;

    public SocketModule(@NonNull SocketIOServer socketIOServer,
                        @NonNull SocketIoSessionManager sessionManager) {
        this.socketIOServer = socketIOServer;
        this.sessionManager = sessionManager;
        socketIOServer.addConnectListener(onConnected());
        socketIOServer.addDisconnectListener(onDisconnected());
        socketIOServer.addEventListener("send_message", MessageDto.class, onChatReceived());
    }

    private ConnectListener onConnected() {
        return (client) -> System.out.format("Socket ID[%s]  Connected to socket%n", client.getSessionId().toString());
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            System.out.format("Client[%s] - Disconnected from socket%n", client.getSessionId().toString());
            sessionManager.removeSessionBySocket(client);
        };
    }

    private DataListener<MessageDto> onChatReceived() {
        return (senderClient, data, ackSender) -> {
            System.out.format("Client message [%s]%n", senderClient.getSessionId().toString());
            switch (data.getType()) {
                case AUTH_REGISTER -> sessionManager.addSession(Long.valueOf(data.getMessage()), senderClient);
                case AUTH_DEREGISTER -> sessionManager.removeSessionBySocket(senderClient);
                default -> throw new IllegalStateException("Unexpected value: " + data.getType());
            }
        };
    }

    @PreDestroy
    public void destroy() {
        socketIOServer.stop();
    }
}
