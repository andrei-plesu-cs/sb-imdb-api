package com.andrei.plesoianu.imdbcloneapi.services.movie;

import com.andrei.plesoianu.imdbcloneapi.enums.MovieType;
import com.andrei.plesoianu.imdbcloneapi.enums.StorageType;
import com.andrei.plesoianu.imdbcloneapi.exceptions.ApiException;
import com.andrei.plesoianu.imdbcloneapi.exceptions.NotFoundException;
import com.andrei.plesoianu.imdbcloneapi.models.Genre;
import com.andrei.plesoianu.imdbcloneapi.models.Movie;
import com.andrei.plesoianu.imdbcloneapi.payloads.event.CreateEventDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.event.EventDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.genre.GenreDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.movie.CompactMovieDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.movie.CreateMovieDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.movie.MovieDto;
import com.andrei.plesoianu.imdbcloneapi.repositories.GenresRepository;
import com.andrei.plesoianu.imdbcloneapi.repositories.MovieRepository;
import com.andrei.plesoianu.imdbcloneapi.services.event.EventService;
import com.andrei.plesoianu.imdbcloneapi.services.parser.ParserService;
import com.andrei.plesoianu.imdbcloneapi.services.storage.StorageService;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;
    private final StorageService storageService;
    private final GenresRepository genresRepository;
    private final EventService eventService;
    private final ParserService parserService;

    public MovieServiceImpl(@NonNull MovieRepository movieRepository,
                            @NonNull ModelMapper modelMapper,
                            @NonNull StorageService storageService,
                            @NonNull GenresRepository genresRepository,
                            @NonNull EventService eventService,
                            @NonNull ParserService parserService) {
        this.movieRepository = movieRepository;
        this.modelMapper = modelMapper;
        this.storageService = storageService;
        this.genresRepository = genresRepository;
        this.eventService = eventService;
        this.parserService = parserService;
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
        } else if (dto.getType() == MovieType.SERIES && (dto.getSeasons() == null || dto.getSeasons().isEmpty())) {
            throw new ApiException("A series must have at least one season");
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

    @Override
    public List<CompactMovieDto> getCompactMovies() {
        return movieRepository.findAll().stream()
                .map(movie -> modelMapper.map(movie, CompactMovieDto.class))
                .toList();
    }

    @Override
    public EventDto parseUrl(String url) {
        var createdEvent = eventService.createEvent(new CreateEventDto("Parse movie url " + url));
        var service = Executors.newSingleThreadExecutor();
        try {
            service.submit(() -> {
                try {
                    parserService.parseMovieUrl(url);
                    eventService.markEventSuccessful(createdEvent.getId());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            });
        } finally {
            service.shutdown();
        }
        return createdEvent;
    }
}
