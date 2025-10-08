package com.andrei.plesoianu.imdbcloneapi.socketio;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketIoConfig {
    @Value("${socket-server.host}")
    private String host;

    @Value("${socket-server.port}")
    private Integer port;

    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(host);
        config.setPort(port);
        return new SocketIOServer(config);
    }

    @Bean
    public CommandLineRunner commandLineRunner(SocketIOServer server) {
        return args -> server.start();
    }
}
