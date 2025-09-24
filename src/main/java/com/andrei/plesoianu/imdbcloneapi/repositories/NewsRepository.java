package com.andrei.plesoianu.imdbcloneapi.repositories;

import com.andrei.plesoianu.imdbcloneapi.models.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findTop5ByOrderByAddedDesc();
}
