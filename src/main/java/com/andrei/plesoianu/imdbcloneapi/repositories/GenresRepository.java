package com.andrei.plesoianu.imdbcloneapi.repositories;

import com.andrei.plesoianu.imdbcloneapi.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenresRepository extends JpaRepository<Genre, Long> {
    boolean existsByName(String name);
}
