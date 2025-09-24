package com.andrei.plesoianu.imdbcloneapi.services.news;

import com.andrei.plesoianu.imdbcloneapi.payloads.news.CreateNewsDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.news.NewsDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NewsService {
    NewsDto addNews(CreateNewsDto dto);

    NewsDto updateBanner(Long newsId, MultipartFile file);

    List<NewsDto> getRecentNews();
}
