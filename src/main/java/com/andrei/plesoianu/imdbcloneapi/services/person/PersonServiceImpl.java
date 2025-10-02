package com.andrei.plesoianu.imdbcloneapi.services.person;

import com.andrei.plesoianu.imdbcloneapi.enums.StorageType;
import com.andrei.plesoianu.imdbcloneapi.exceptions.ApiException;
import com.andrei.plesoianu.imdbcloneapi.exceptions.NotFoundException;
import com.andrei.plesoianu.imdbcloneapi.models.Person;
import com.andrei.plesoianu.imdbcloneapi.payloads.person.PersonDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.person.CreatePersonDto;
import com.andrei.plesoianu.imdbcloneapi.repositories.PersonRepository;
import com.andrei.plesoianu.imdbcloneapi.services.storage.StorageService;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;
    private final StorageService storageService;

    public PersonServiceImpl(@NonNull PersonRepository personRepository,
                             @NonNull ModelMapper modelMapper,
                             @NonNull StorageService storageService) {
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
        this.storageService = storageService;
    }

    @Override
    public PersonDto addPerson(CreatePersonDto personDto) {
        Person person = modelMapper.map(personDto, Person.class);
        Person createdPerson = personRepository.save(person);
        return modelMapper.map(createdPerson, PersonDto.class);
    }

    @Override
    public PersonDto updateProfileImage(Long personId, MultipartFile file) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new NotFoundException(Person.class, personId));

        try {
            var fileName = storageService.store(StorageType.PERSON, file);
            person.setProfileImageUrl(fileName);
            return modelMapper.map(personRepository.save(person), PersonDto.class);
        } catch (IOException e) {
            throw new ApiException(e.getMessage());
        }
    }

    @Override
    public PersonDto getPerson(Long personId) {
        var person = personRepository.findById(personId)
                .orElseThrow(() -> new NotFoundException(Person.class, personId));

        return modelMapper.map(person, PersonDto.class);
    }

    @Override
    public List<PersonDto> getAllPeople() {
        return personRepository.findAll().stream()
                .map(person -> modelMapper.map(person, PersonDto.class))
                .toList();
    }
}
