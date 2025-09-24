package com.andrei.plesoianu.imdbcloneapi.payloads.movie;

import com.andrei.plesoianu.imdbcloneapi.enums.MovieType;
import com.andrei.plesoianu.imdbcloneapi.payloads.genre.GenreDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMovieDto {
    @NotBlank
    @Size(min = 5)
    private String title;

    @Size(min = 10)
    private String overview;

    @NotBlank
    private String ageRestriction;

    @NotNull
    private MovieType type;

    private LocalDate releaseDate;

    private List<GenreDto> genres;
}
