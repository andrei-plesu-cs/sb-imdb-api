package com.andrei.plesoianu.imdbcloneapi.models;

import com.andrei.plesoianu.imdbcloneapi.enums.MovieType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String posterUrl;

    @NotNull
    @Column(unique = true)
    @Size(min = 5)
    private String title;

    @NotNull
    @Column(columnDefinition = "TEXT")
    @Size(min = 10)
    private String overview;

    @NotNull
    @Column(length = 20)
    private String ageRestriction;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private MovieType type;

    private LocalDateTime added = LocalDateTime.now();

    private LocalDate releaseDate;

    @OneToMany(mappedBy = "movie", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Character> characters = new ArrayList<>();

    @ManyToMany(cascade = { CascadeType.MERGE })
    @JoinTable(name = "movie_contributors",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "contributor_id"))
    @ToString.Exclude
    private List<Contributor> contributors = new ArrayList<>();

    @OneToMany(mappedBy = "movie", orphanRemoval = true)
    @ToString.Exclude
    private List<News> news = new ArrayList<>();

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "movie_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @ToString.Exclude
    private List<Genre> genres = new ArrayList<>();
}
