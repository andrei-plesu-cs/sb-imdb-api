package com.andrei.plesoianu.imdbcloneapi.controllers;

import com.andrei.plesoianu.imdbcloneapi.payloads.genre.CreateGenreDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.genre.GenreDto;
import com.andrei.plesoianu.imdbcloneapi.services.genre.GenreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<List<GenreDto>> getAllGenres() {
        return ResponseEntity.ok(genreService.getAllGenres());
    }

    @PostMapping
    public ResponseEntity<GenreDto> createGenre(@Valid @RequestBody CreateGenreDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(genreService.addGenre(dto));
    }
}
