package com.andrei.plesoianu.imdbcloneapi.payloads.movie;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParseUrlDto {
    @NotEmpty
    private String url;
}
