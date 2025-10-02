package com.andrei.plesoianu.imdbcloneapi.payloads.contributor;

import com.andrei.plesoianu.imdbcloneapi.enums.MovieRole;
import com.andrei.plesoianu.imdbcloneapi.payloads.person.PersonDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContributorDto {
    private Long id;
    private MovieRole role;
    private PersonDto person;
}
