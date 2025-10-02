package com.andrei.plesoianu.imdbcloneapi.payloads.contributor;

import com.andrei.plesoianu.imdbcloneapi.enums.MovieRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateContributorDto {
    @NotNull
    private Long personId;

    @NotNull
    private MovieRole role;
}
