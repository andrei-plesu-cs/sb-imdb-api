package com.andrei.plesoianu.imdbcloneapi.payloads.contributor;

import com.andrei.plesoianu.imdbcloneapi.enums.MovieRole;
import com.andrei.plesoianu.imdbcloneapi.payloads.base.PersonDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContributorDto extends PersonDto {
    private MovieRole role;
}
