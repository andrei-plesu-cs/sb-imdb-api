package com.andrei.plesoianu.imdbcloneapi.controllers;

import com.andrei.plesoianu.imdbcloneapi.payloads.genre.CreateGenreDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.genre.GenreDto;
import com.andrei.plesoianu.imdbcloneapi.services.genre.GenreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping
    public ResponseEntity<GenreDto> createGenre(@Valid @RequestBody CreateGenreDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(genreService.addGenre(dto));
    }
}
