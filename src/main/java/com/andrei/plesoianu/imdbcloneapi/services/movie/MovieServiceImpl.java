package com.andrei.plesoianu.imdbcloneapi.services.movie;

import com.andrei.plesoianu.imdbcloneapi.enums.MovieType;
import com.andrei.plesoianu.imdbcloneapi.enums.StorageType;
import com.andrei.plesoianu.imdbcloneapi.exceptions.ApiException;
import com.andrei.plesoianu.imdbcloneapi.exceptions.NotFoundException;
import com.andrei.plesoianu.imdbcloneapi.models.Genre;
import com.andrei.plesoianu.imdbcloneapi.models.Movie;
import com.andrei.plesoianu.imdbcloneapi.payloads.genre.GenreDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.movie.CompactMovieDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.movie.CreateMovieDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.movie.MovieDto;
import com.andrei.plesoianu.imdbcloneapi.repositories.GenresRepository;
import com.andrei.plesoianu.imdbcloneapi.repositories.MovieRepository;
import com.andrei.plesoianu.imdbcloneapi.services.storage.StorageService;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;
    private final StorageService storageService;
    private final GenresRepository genresRepository;

    public MovieServiceImpl(@NonNull MovieRepository movieRepository,
                            @NonNull ModelMapper modelMapper,
                            @NonNull StorageService storageService,
                            @NonNull GenresRepository genresRepository) {
        this.movieRepository = movieRepository;
        this.modelMapper = modelMapper;
        this.storageService = storageService;
        this.genresRepository = genresRepository;
    }

    @Override
    public MovieDto getMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new NotFoundException(Movie.class, movieId));

        return modelMapper.map(movie, MovieDto.class);
    }

    @Override
    public MovieDto addMovie(CreateMovieDto dto) {
        if (dto.getType() == MovieType.MOVIE && dto.getReleaseDate() == null) {
            throw new ApiException("Movies must have a release date");
        }

        var movie = modelMapper.map(dto, Movie.class);

        List<Genre> genres = genresRepository.findAllById(dto.getGenres().stream().map(GenreDto::getId).toList());

        movie.setGenres(genres);

        var createdMovie = movieRepository.save(movie);

        return modelMapper.map(createdMovie, MovieDto.class);
    }

    @Override
    public MovieDto updatePoster(Long movieId, MultipartFile file) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new NotFoundException(Movie.class, movieId));

        try {
            var fileName = storageService.store(StorageType.MOVIE, file);
            movie.setPosterUrl(fileName);
            return modelMapper.map(movieRepository.save(movie), MovieDto.class);
        } catch (IOException e) {
            throw new ApiException(e.getMessage());
        }
    }

    @Override
    public List<CompactMovieDto> getRecentMovies() {
        var movies = movieRepository.findTop10ByOrderByAddedDesc();
        return movies.stream()
                .map(movie -> modelMapper.map(movie, CompactMovieDto.class))
                .toList();
    }
}
