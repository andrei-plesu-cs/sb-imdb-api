package com.andrei.plesoianu.imdbcloneapi.models;

import com.andrei.plesoianu.imdbcloneapi.enums.JobStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private LocalDateTime added = LocalDateTime.now();

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private JobStatus status = JobStatus.PENDING;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;
}
