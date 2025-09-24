package com.andrei.plesoianu.imdbcloneapi.services.movie;

import com.andrei.plesoianu.imdbcloneapi.payloads.movie.CompactMovieDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.movie.CreateMovieDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.movie.MovieDto;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface MovieService {
    MovieDto getMovie(Long movieId);

    MovieDto addMovie(CreateMovieDto dto);

    MovieDto updatePoster(Long movieId, MultipartFile file);

    List<CompactMovieDto> getRecentMovies();
}
