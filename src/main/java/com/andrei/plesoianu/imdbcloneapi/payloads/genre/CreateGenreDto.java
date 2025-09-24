package com.andrei.plesoianu.imdbcloneapi.payloads.genre;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateGenreDto {
    @NotNull
    @Size(min = 4, max = 30)
    private String name;
}
