package com.andrei.plesoianu.imdbcloneapi.models;

import com.andrei.plesoianu.imdbcloneapi.enums.MovieRole;
import com.andrei.plesoianu.imdbcloneapi.models.base.UserDetails;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "contributors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contributor extends UserDetails {
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private MovieRole role;
}
