package com.andrei.plesoianu.imdbcloneapi.services.character;

import com.andrei.plesoianu.imdbcloneapi.payloads.character.CharacterDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.character.CreateCharacterDto;

public interface CharacterService {
    CharacterDto addCharacter(CreateCharacterDto dto);
}
