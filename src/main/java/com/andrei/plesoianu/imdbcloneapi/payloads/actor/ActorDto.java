package com.andrei.plesoianu.imdbcloneapi.payloads.actor;

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
