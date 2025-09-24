package com.andrei.plesoianu.imdbcloneapi.payloads.actor;

import com.andrei.plesoianu.imdbcloneapi.payloads.base.CreatePersonDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateActorDto extends CreatePersonDto {
    public CreateActorDto(String name) {
        super(name);
    }
}
