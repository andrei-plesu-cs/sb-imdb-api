package com.andrei.plesoianu.imdbcloneapi.controllers;

import com.andrei.plesoianu.imdbcloneapi.payloads.movie.CompactMovieDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.movie.CreateMovieDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.movie.MovieDto;
import com.andrei.plesoianu.imdbcloneapi.services.movie.MovieService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(@NonNull MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/recent")
    public ResponseEntity<List<CompactMovieDto>> getRecentMovies() {
        return ResponseEntity.ok(movieService.getRecentMovies());
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDto> getMovie(@PathVariable Long movieId) {
        return ResponseEntity.ok(movieService.getMovie(movieId));
    }

    @PostMapping()
    public ResponseEntity<MovieDto> addMovie(@Valid @RequestBody CreateMovieDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.addMovie(dto));
    }

    @PutMapping("/image/{movieId}")
    public ResponseEntity<MovieDto> updatePoster(@PathVariable Long movieId, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(movieService.updatePoster(movieId, file));
    }
}
