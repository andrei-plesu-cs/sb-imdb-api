package com.andrei.plesoianu.imdbcloneapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity(name = "news")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 10)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String bannerUrl;

    private String buttonTitle;

    @PositiveOrZero
    private Integer reactions;

    @Positive
    private Integer duration;

    private LocalDateTime added = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "movie_id")
    @ToString.Exclude
    private Movie movie;
}
