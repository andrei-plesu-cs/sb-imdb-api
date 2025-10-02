package com.andrei.plesoianu.imdbcloneapi.services.person;

import com.andrei.plesoianu.imdbcloneapi.payloads.person.PersonDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.person.CreatePersonDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PersonService {
    PersonDto addPerson(CreatePersonDto personDto);

    PersonDto updateProfileImage(Long personId, MultipartFile file);

    PersonDto getPerson(Long personId);

    List<PersonDto> getAllPeople();
}
