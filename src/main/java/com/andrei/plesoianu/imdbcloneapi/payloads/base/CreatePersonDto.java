package com.andrei.plesoianu.imdbcloneapi.payloads.base;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePersonDto {
    @NotBlank
    @Size(min = 10)
    private String name;
}
