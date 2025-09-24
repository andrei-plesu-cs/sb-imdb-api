package com.andrei.plesoianu.imdbcloneapi.services.genre;

import com.andrei.plesoianu.imdbcloneapi.payloads.genre.CreateGenreDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.genre.GenreDto;
import jakarta.validation.Valid;

public interface GenreService {
    GenreDto addGenre(@Valid CreateGenreDto dto);
}
