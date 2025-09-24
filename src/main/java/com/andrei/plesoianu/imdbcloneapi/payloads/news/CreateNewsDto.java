package com.andrei.plesoianu.imdbcloneapi.payloads.news;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNewsDto {
    @NotBlank
    private String title;

    private String description;

    @NotBlank
    private String buttonTitle;

    @NotNull
    @PositiveOrZero
    private Integer reactions;

    @NotNull
    @Positive
    private Integer duration;

    @NotNull
    private Long movieId;
}
