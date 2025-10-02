package com.andrei.plesoianu.imdbcloneapi.services.contributor;

import com.andrei.plesoianu.imdbcloneapi.exceptions.NotFoundException;
import com.andrei.plesoianu.imdbcloneapi.models.Contributor;
import com.andrei.plesoianu.imdbcloneapi.models.Movie;
import com.andrei.plesoianu.imdbcloneapi.models.Person;
import com.andrei.plesoianu.imdbcloneapi.payloads.contributor.ContributorDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.contributor.CreateContributorDto;
import com.andrei.plesoianu.imdbcloneapi.repositories.ContributorRepository;
import com.andrei.plesoianu.imdbcloneapi.repositories.MovieRepository;
import com.andrei.plesoianu.imdbcloneapi.repositories.PersonRepository;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ContributorServiceImpl implements ContributorService {
    private final MovieRepository movieRepository;
    private final PersonRepository personRepository;
    private final ContributorRepository contributorRepository;
    private final ModelMapper modelMapper;

    public ContributorServiceImpl(@NonNull MovieRepository movieRepository,
                                  @NonNull PersonRepository personRepository,
                                  @NonNull ContributorRepository contributorRepository,
                                  @NonNull ModelMapper modelMapper) {
        this.movieRepository = movieRepository;
        this.personRepository = personRepository;
        this.contributorRepository = contributorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ContributorDto addContributor(Long movieId, CreateContributorDto dto) {
        var movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new NotFoundException(Movie.class, movieId));

        var person = personRepository.findById(movieId)
                .orElseThrow(() -> new NotFoundException(Person.class, dto.getPersonId()));

        var contributor = new Contributor();
        contributor.setMovie(movie);
        contributor.setRole(dto.getRole());
        contributor.setPerson(person);

        return modelMapper.map(contributorRepository.save(contributor), ContributorDto.class);
    }
}
