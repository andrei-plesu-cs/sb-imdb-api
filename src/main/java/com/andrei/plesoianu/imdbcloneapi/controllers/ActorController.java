package com.andrei.plesoianu.imdbcloneapi.controllers;

import com.andrei.plesoianu.imdbcloneapi.payloads.actor.ActorDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.actor.CreateActorDto;
import com.andrei.plesoianu.imdbcloneapi.services.actor.ActorService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/actors")
public class ActorController {
    private final ActorService actorService;

    public ActorController(@NonNull ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    public ResponseEntity<List<ActorDto>> getAllActors() {
        return ResponseEntity.ok(actorService.getAllActors());
    }

    @GetMapping("/{actorId}")
    public ResponseEntity<ActorDto> getActor(@PathVariable Long actorId) {
        return ResponseEntity.ok(actorService.getActor(actorId));
    }

    @PostMapping()
    public ResponseEntity<ActorDto> addActor(@Valid @RequestBody CreateActorDto actorDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(actorService.addActor(actorDto));
    }

    @PutMapping("/image/{actorId}")
    public ResponseEntity<ActorDto> updateProfileImage(@PathVariable Long actorId, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(actorService.updateProfileImage(actorId, file));
    }
}
