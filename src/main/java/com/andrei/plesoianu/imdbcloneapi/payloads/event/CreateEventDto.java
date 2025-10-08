package com.andrei.plesoianu.imdbcloneapi.payloads.event;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventDto {
    @NotNull
    private String title;

    private Long userId;
}
