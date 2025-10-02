package com.andrei.plesoianu.imdbcloneapi.payloads.movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompactMovieDto {
    private Long id;
    private String title;
    private String posterUrl;
}
