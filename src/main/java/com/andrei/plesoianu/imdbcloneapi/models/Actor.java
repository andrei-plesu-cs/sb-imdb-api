package com.andrei.plesoianu.imdbcloneapi.models;

import com.andrei.plesoianu.imdbcloneapi.models.base.UserDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "actors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Actor extends UserDetails {
    @OneToMany(mappedBy = "actor", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @ToString.Exclude
    private List<Character> characters = new ArrayList<>();
}
