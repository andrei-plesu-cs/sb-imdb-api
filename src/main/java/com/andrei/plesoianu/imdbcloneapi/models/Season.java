package com.andrei.plesoianu.imdbcloneapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seasons")
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate releaseDate;

    @NotNull
    @Positive
    private Integer episodes;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
}
