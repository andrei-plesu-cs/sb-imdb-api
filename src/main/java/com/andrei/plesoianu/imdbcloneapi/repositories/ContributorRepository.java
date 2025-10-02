package com.andrei.plesoianu.imdbcloneapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.andrei.plesoianu.imdbcloneapi.models.Contributor;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributorRepository extends JpaRepository<Contributor, Long> {
}
