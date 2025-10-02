package com.andrei.plesoianu.imdbcloneapi.services.character;

import com.andrei.plesoianu.imdbcloneapi.exceptions.NotFoundException;
import com.andrei.plesoianu.imdbcloneapi.models.Character;
import com.andrei.plesoianu.imdbcloneapi.models.Movie;
import com.andrei.plesoianu.imdbcloneapi.models.Person;
import com.andrei.plesoianu.imdbcloneapi.payloads.character.CharacterDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.character.CreateCharacterDto;
import com.andrei.plesoianu.imdbcloneapi.repositories.PersonRepository;
import com.andrei.plesoianu.imdbcloneapi.repositories.CharacterRepository;
import com.andrei.plesoianu.imdbcloneapi.repositories.MovieRepository;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CharacterServiceImpl implements CharacterService {
    private final PersonRepository actorRepository;
    private final CharacterRepository characterRepository;
    private final ModelMapper modelMapper;
    private final MovieRepository movieRepository;

    public CharacterServiceImpl(@NonNull PersonRepository actorRepository,
                                @NonNull CharacterRepository characterRepository,
                                @NonNull ModelMapper modelMapper, MovieRepository movieRepository) {
        this.actorRepository = actorRepository;
        this.characterRepository = characterRepository;
        this.modelMapper = modelMapper;
        this.movieRepository = movieRepository;
    }

    @Override
    public CharacterDto addCharacter(CreateCharacterDto dto) {
        Person actor = actorRepository.findById(dto.getActorId())
                .orElseThrow(() -> new NotFoundException(Person.class, dto.getActorId()));

        Movie movie = movieRepository.findById(dto.getMovieId())
                .orElseThrow(() -> new NotFoundException(Movie.class, dto.getMovieId()));

        Character character = new Character();
        character.setName(dto.getName());
        character.setActor(actor);
        character.setMovie(movie);
        var createdCharacter = characterRepository.save(character);
        return modelMapper.map(createdCharacter, CharacterDto.class);
    }
}
