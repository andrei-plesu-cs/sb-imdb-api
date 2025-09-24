package com.andrei.plesoianu.imdbcloneapi.payloads.genre;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreDto {
    private Long id;
    private String name;
}
