package com.andrei.plesoianu.imdbcloneapi.services.event;

import com.andrei.plesoianu.imdbcloneapi.payloads.event.CreateEventDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.event.EventDto;

import java.util.List;

public interface EventService {
    List<EventDto> getEvents();
    EventDto createEvent(CreateEventDto dto);
    EventDto markEventSuccessful(Long eventId);
}
