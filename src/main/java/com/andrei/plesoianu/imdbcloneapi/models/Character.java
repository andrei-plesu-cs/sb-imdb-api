package com.andrei.plesoianu.imdbcloneapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "characters")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5)
    private String name;

    @ManyToOne
    @JoinColumn(name = "actor_id")
    @ToString.Exclude
    private Actor actor;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    @ToString.Exclude
    private Movie movie;
}
