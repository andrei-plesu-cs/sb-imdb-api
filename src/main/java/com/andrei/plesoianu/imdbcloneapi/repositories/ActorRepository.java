package com.andrei.plesoianu.imdbcloneapi.repositories;

import com.andrei.plesoianu.imdbcloneapi.models.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends JpaRepository<Actor,Long> {
}
