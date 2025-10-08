package com.andrei.plesoianu.imdbcloneapi.payloads.event;

import com.andrei.plesoianu.imdbcloneapi.enums.JobStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private Long id;
    private String title;
    private LocalDateTime added;
    private JobStatus status;
}
