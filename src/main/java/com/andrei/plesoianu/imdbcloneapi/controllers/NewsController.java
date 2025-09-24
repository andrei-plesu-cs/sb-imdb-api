package com.andrei.plesoianu.imdbcloneapi.controllers;

import com.andrei.plesoianu.imdbcloneapi.payloads.news.CreateNewsDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.news.NewsDto;
import com.andrei.plesoianu.imdbcloneapi.services.news.NewsService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    private final NewsService newsService;

    public NewsController(@NonNull NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping
    public ResponseEntity<NewsDto> createNews(@Valid @RequestBody CreateNewsDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(newsService.addNews(dto));
    }

    @PutMapping("/image/{newsId}")
    public ResponseEntity<NewsDto> updateBaner(@PathVariable Long newsId, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(newsService.updateBanner(newsId, file));
    }

    @GetMapping
    public ResponseEntity<List<NewsDto>> getRecent() {
        return ResponseEntity.ok(newsService.getRecentNews());
    }
}
