package com.andrei.plesoianu.imdbcloneapi.payloads.socketio;

import com.andrei.plesoianu.imdbcloneapi.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private String message;
    private MessageType type;
}
