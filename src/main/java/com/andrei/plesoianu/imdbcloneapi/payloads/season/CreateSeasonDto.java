package com.andrei.plesoianu.imdbcloneapi.payloads.season;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSeasonDto {
    @NotNull
    private LocalDate releaseDate;

    @NotNull
    @Positive
    private Integer episodes;
}
