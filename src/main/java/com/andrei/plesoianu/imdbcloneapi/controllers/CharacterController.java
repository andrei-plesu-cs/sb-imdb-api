package com.andrei.plesoianu.imdbcloneapi.controllers;

import com.andrei.plesoianu.imdbcloneapi.payloads.character.CharacterDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.character.CreateCharacterDto;
import com.andrei.plesoianu.imdbcloneapi.services.character.CharacterService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {
    private final CharacterService characterService;

    public CharacterController(@NonNull CharacterService characterService) {
        this.characterService = characterService;
    }

    @PostMapping()
    public ResponseEntity<CharacterDto> addCharacter(@Valid @RequestBody CreateCharacterDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(characterService.addCharacter(dto));
    }
}
