package com.andrei.plesoianu.imdbcloneapi.payloads.movie;

import com.andrei.plesoianu.imdbcloneapi.enums.MovieType;
import com.andrei.plesoianu.imdbcloneapi.payloads.character.CharacterDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.contributor.ContributorDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.genre.GenreDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {
    private Long id;
    private String posterUrl;
    private String title;
    private String overview;
    private String ageRestriction;
    private LocalDate releaseDate;
    private MovieType type;
    private List<CharacterDto> characters = new ArrayList<>();
    private List<ContributorDto> contributors = new ArrayList<>();
    private List<GenreDto> genres = new ArrayList<>();
}
