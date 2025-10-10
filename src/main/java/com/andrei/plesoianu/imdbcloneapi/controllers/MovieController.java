package com.andrei.plesoianu.imdbcloneapi.controllers;

import com.andrei.plesoianu.imdbcloneapi.payloads.contributor.ContributorDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.contributor.CreateContributorDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.event.EventDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.movie.CompactMovieDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.movie.CreateMovieDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.movie.MovieDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.movie.ParseUrlDto;
import com.andrei.plesoianu.imdbcloneapi.services.contributor.ContributorService;
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
    private final ContributorService contributorService;

    public MovieController(@NonNull MovieService movieService,
                           @NonNull ContributorService contributorService) {
        this.movieService = movieService;
        this.contributorService = contributorService;
    }

    @GetMapping("/recent")
    public ResponseEntity<List<CompactMovieDto>> getRecentMovies() {
        return ResponseEntity.ok(movieService.getRecentMovies());
    }

    @GetMapping("/compact")
    public ResponseEntity<List<CompactMovieDto>> getCompactMovies() {
        return ResponseEntity.ok(movieService.getCompactMovies());
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDto> getMovie(@PathVariable Long movieId) {
        return ResponseEntity.ok(movieService.getMovie(movieId));
    }

    @PostMapping()
    public ResponseEntity<MovieDto> addMovie(@Valid @RequestBody CreateMovieDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.addMovie(dto));
    }

    @PostMapping("/contributor/{movieId}")
    public ResponseEntity<ContributorDto> createContributor(
            @PathVariable Long movieId,
            @Valid @RequestBody CreateContributorDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contributorService.addContributor(movieId, dto));
    }

    @PutMapping("/image/{movieId}")
    public ResponseEntity<MovieDto> updatePoster(@PathVariable Long movieId, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(movieService.updatePoster(movieId, file));
    }

    @PostMapping("/parse-url")
    public ResponseEntity<EventDto> parseUrl(@Valid @RequestBody ParseUrlDto dto) {
        return ResponseEntity.ok(movieService.parseUrl(dto.getUrl()));
    }
}
