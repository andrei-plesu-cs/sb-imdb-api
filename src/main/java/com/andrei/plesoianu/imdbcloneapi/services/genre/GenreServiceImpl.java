package com.andrei.plesoianu.imdbcloneapi.services.genre;

import com.andrei.plesoianu.imdbcloneapi.exceptions.ApiException;
import com.andrei.plesoianu.imdbcloneapi.models.Genre;
import com.andrei.plesoianu.imdbcloneapi.payloads.genre.CreateGenreDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.genre.GenreDto;
import com.andrei.plesoianu.imdbcloneapi.repositories.GenresRepository;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenresRepository genresRepository;
    private final ModelMapper modelMapper;

    public GenreServiceImpl(@NonNull GenresRepository genresRepository,
                            @NonNull ModelMapper modelMapper) {
        this.genresRepository = genresRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public GenreDto addGenre(CreateGenreDto dto) {
        if (genresRepository.existsByName(dto.getName())) {
            throw new ApiException("Already a genre with name " + dto.getName());
        }

        var genre = modelMapper.map(dto, Genre.class);
        return modelMapper.map(genresRepository.save(genre), GenreDto.class);
    }

    @Override
    public List<GenreDto> getAllGenres() {
        return genresRepository.findAll().stream()
                .map(genre -> modelMapper.map(genre, GenreDto.class))
                .toList();
    }
}
