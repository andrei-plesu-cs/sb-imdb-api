package com.andrei.plesoianu.imdbcloneapi.payloads.base;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class PersonDto {
    private Long id;
    private String name;
    private String profileImageUrl;
}
