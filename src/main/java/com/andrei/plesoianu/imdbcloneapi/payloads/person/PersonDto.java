package com.andrei.plesoianu.imdbcloneapi.payloads.person;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersonDto {
    private Long id;
    private String name;
    private String profileImageUrl;
}
