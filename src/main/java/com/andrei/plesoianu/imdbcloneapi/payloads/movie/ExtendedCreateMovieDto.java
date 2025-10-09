package com.andrei.plesoianu.imdbcloneapi.payloads.movie;

import com.andrei.plesoianu.imdbcloneapi.payloads.character.CharacterDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.contributor.ContributorDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtendedCreateMovieDto extends CreateMovieDto {
    private List<CharacterDto> characters;
    private List<ContributorDto> contributors;
}
