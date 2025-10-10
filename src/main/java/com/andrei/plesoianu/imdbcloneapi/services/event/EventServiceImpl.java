package com.andrei.plesoianu.imdbcloneapi.services.event;

import com.andrei.plesoianu.imdbcloneapi.enums.JobStatus;
import com.andrei.plesoianu.imdbcloneapi.exceptions.NotFoundException;
import com.andrei.plesoianu.imdbcloneapi.models.Event;
import com.andrei.plesoianu.imdbcloneapi.payloads.event.CreateEventDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.event.EventDto;
import com.andrei.plesoianu.imdbcloneapi.repositories.EventRepository;
import com.andrei.plesoianu.imdbcloneapi.security.AuthUtil;
import com.andrei.plesoianu.imdbcloneapi.socketio.SocketIoSessionManager;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final AuthUtil authUtil;
    private final SocketIoSessionManager sessionManager;

    public EventServiceImpl(@NonNull EventRepository eventRepository,
                            @NonNull ModelMapper modelMapper,
                            @NonNull AuthUtil authUtil,
                            @NonNull SocketIoSessionManager sessionManager) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
        this.authUtil = authUtil;
        this.sessionManager = sessionManager;
    }

    @Override
    public List<EventDto> getEvents() {
        var userId = authUtil.loggedInUser().getId();
        return eventRepository.findByUserIdOrderByAddedDesc(userId).stream()
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

    @Override
    public EventDto markEventSuccessful(Long eventId) {
        var event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(Event.class, eventId));

        event.setStatus(JobStatus.COMPLETED);
        var updatedEvent = modelMapper.map(eventRepository.save(event), EventDto.class);

        var session = sessionManager.getSession(event.getUser().getId());
        session.sendEvent("job_completed", updatedEvent.getId());

        return updatedEvent;
    }

    @Override
    public void deleteEvents() {
        var userId = authUtil.loggedInUser().getId();
        eventRepository.deleteAllByUserIdAndStatusIn(userId, List.of(JobStatus.COMPLETED, JobStatus.CANCELED));
    }
}
