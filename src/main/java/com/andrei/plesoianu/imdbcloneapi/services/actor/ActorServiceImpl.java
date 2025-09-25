package com.andrei.plesoianu.imdbcloneapi.services.actor;

import com.andrei.plesoianu.imdbcloneapi.enums.StorageType;
import com.andrei.plesoianu.imdbcloneapi.exceptions.ApiException;
import com.andrei.plesoianu.imdbcloneapi.exceptions.NotFoundException;
import com.andrei.plesoianu.imdbcloneapi.models.Actor;
import com.andrei.plesoianu.imdbcloneapi.payloads.actor.ActorDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.actor.CreateActorDto;
import com.andrei.plesoianu.imdbcloneapi.repositories.ActorRepository;
import com.andrei.plesoianu.imdbcloneapi.services.storage.StorageService;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ActorServiceImpl implements ActorService {
    private final ActorRepository actorRepository;
    private final ModelMapper modelMapper;
    private final StorageService storageService;

    public ActorServiceImpl(@NonNull ActorRepository actorRepository,
                            @NonNull ModelMapper modelMapper,
                            @NonNull StorageService storageService) {
        this.actorRepository = actorRepository;
        this.modelMapper = modelMapper;
        this.storageService = storageService;
    }

    @Override
    public ActorDto addActor(CreateActorDto actorDto) {
        Actor actor = modelMapper.map(actorDto, Actor.class);
        Actor createdActor = actorRepository.save(actor);
        return modelMapper.map(createdActor, ActorDto.class);
    }

    @Override
    public ActorDto updateProfileImage(Long actorId, MultipartFile file) {
        Actor actor = actorRepository.findById(actorId)
                .orElseThrow(() -> new NotFoundException(Actor.class, actorId));

        try {
            var fileName = storageService.store(StorageType.PERSON, file);
            actor.setProfileImageUrl(fileName);
            return modelMapper.map(actorRepository.save(actor), ActorDto.class);
        } catch (IOException e) {
            throw new ApiException(e.getMessage());
        }
    }

    @Override
    public ActorDto getActor(Long actorId) {
        var actor = actorRepository.findById(actorId)
                .orElseThrow(() -> new NotFoundException(Actor.class, actorId));

        return modelMapper.map(actor, ActorDto.class);
    }

    @Override
    public List<ActorDto> getAllActors() {
        return actorRepository.findAll().stream()
                .map(actor -> modelMapper.map(actor, ActorDto.class))
                .toList();
    }
}
