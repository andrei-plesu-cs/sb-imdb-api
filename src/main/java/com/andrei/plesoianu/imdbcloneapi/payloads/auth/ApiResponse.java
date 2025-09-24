package com.andrei.plesoianu.imdbcloneapi.payloads.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private T message;
    private boolean status;
}
