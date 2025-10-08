package com.andrei.plesoianu.imdbcloneapi.services.event;

import com.andrei.plesoianu.imdbcloneapi.models.Event;
import com.andrei.plesoianu.imdbcloneapi.payloads.event.CreateEventDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.event.EventDto;
import com.andrei.plesoianu.imdbcloneapi.repositories.EventRepository;
import com.andrei.plesoianu.imdbcloneapi.security.AuthUtil;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final AuthUtil authUtil;

    public EventServiceImpl(@NonNull EventRepository eventRepository,
                            @NonNull ModelMapper modelMapper,
                            @NonNull AuthUtil authUtil) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
        this.authUtil = authUtil;
    }

    @Override
    public List<EventDto> getEvents() {
        return eventRepository.findByUserIdOrderByAddedDesc(1L).stream()
                .map(event -> modelMapper.map(event, EventDto.class))
                .toList();
    }

    @Override
    public EventDto createEvent(CreateEventDto dto) {
        var event = new Event();
        event.setTitle(dto.getTitle());
        event.setUser(authUtil.loggedInUser());

        return modelMapper.map(eventRepository.save(event), EventDto.class);
    }
}
