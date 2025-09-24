package com.andrei.plesoianu.imdbcloneapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.andrei.plesoianu.imdbcloneapi.models.Character;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {
}
