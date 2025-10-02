package com.andrei.plesoianu.imdbcloneapi.models;

import com.andrei.plesoianu.imdbcloneapi.enums.MovieRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contributors")
public class Contributor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private MovieRole role;

    @ManyToOne
    @JoinColumn(name = "person_id")
    @ToString.Exclude
    private Person person;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    @ToString.Exclude
    private Movie movie;
}
