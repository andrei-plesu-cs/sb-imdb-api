package com.andrei.plesoianu.imdbcloneapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "people")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    @Column(unique = true)
    @Size(min = 10)
    private String name;

    private String profileImageUrl;

    @OneToMany(mappedBy = "actor", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @ToString.Exclude
    private List<Character> characters = new ArrayList<>();
}
