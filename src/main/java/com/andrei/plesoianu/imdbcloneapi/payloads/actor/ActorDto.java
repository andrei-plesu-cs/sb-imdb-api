package com.andrei.plesoianu.imdbcloneapi.payloads.actor;

import com.andrei.plesoianu.imdbcloneapi.payloads.base.PersonDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ActorDto {
    private Long id;
    private String name;
    private String profileImageUrl;
}
