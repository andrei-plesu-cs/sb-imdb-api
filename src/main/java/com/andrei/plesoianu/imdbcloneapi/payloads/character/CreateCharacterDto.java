package com.andrei.plesoianu.imdbcloneapi.payloads.character;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCharacterDto {
    @NotNull
    private Long actorId;

    @NotNull
    private Long movieId;

    @NotBlank
    @Size(min = 5)
    private String name;
}
