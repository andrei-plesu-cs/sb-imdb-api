package com.andrei.plesoianu.imdbcloneapi.controllers;

import com.andrei.plesoianu.imdbcloneapi.payloads.person.PersonDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.person.CreatePersonDto;
import com.andrei.plesoianu.imdbcloneapi.services.person.PersonService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/people")
public class PersonController {
    private final PersonService personService;

    public PersonController(@NonNull PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<PersonDto>> getAllPeople() {
        return ResponseEntity.ok(personService.getAllPeople());
    }

    @GetMapping("/{personId}")
    public ResponseEntity<PersonDto> getPerson(@PathVariable Long personId) {
        return ResponseEntity.ok(personService.getPerson(personId));
    }

    @PostMapping()
    public ResponseEntity<PersonDto> addPerson(@Valid @RequestBody CreatePersonDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.addPerson(dto));
    }

    @PutMapping("/image/{personId}")
    public ResponseEntity<PersonDto> updateProfileImage(@PathVariable Long personId, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(personService.updateProfileImage(personId, file));
    }
}
