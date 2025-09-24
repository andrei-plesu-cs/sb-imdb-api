package com.andrei.plesoianu.imdbcloneapi.payloads.character;

import com.andrei.plesoianu.imdbcloneapi.payloads.actor.ActorDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterDto {
    private Long id;
    private String name;
    private ActorDto actor;
}
