package com.andrei.plesoianu.imdbcloneapi.repositories;

import com.andrei.plesoianu.imdbcloneapi.enums.JobStatus;
import com.andrei.plesoianu.imdbcloneapi.models.Event;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByUserIdOrderByAddedDesc(Long userId);

    @Transactional
    void deleteAllByUserIdAndStatusIn(Long userId, List<JobStatus> status);
}
