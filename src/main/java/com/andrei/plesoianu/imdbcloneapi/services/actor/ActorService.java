package com.andrei.plesoianu.imdbcloneapi.services.actor;

import com.andrei.plesoianu.imdbcloneapi.payloads.actor.ActorDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.actor.CreateActorDto;
import org.springframework.web.multipart.MultipartFile;

public interface ActorService {
    ActorDto addActor(CreateActorDto actorDto);

    ActorDto updateProfileImage(Long actorId, MultipartFile file);

    ActorDto getActor(Long actorId);
}
