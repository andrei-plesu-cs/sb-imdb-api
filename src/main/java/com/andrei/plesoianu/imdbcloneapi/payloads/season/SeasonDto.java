package com.andrei.plesoianu.imdbcloneapi.payloads.season;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeasonDto {
    private Long id;
    private LocalDate releaseDate;
    private Integer episodes;
}
