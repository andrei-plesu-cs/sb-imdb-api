package com.andrei.plesoianu.imdbcloneapi.services.news;

import com.andrei.plesoianu.imdbcloneapi.enums.StorageType;
import com.andrei.plesoianu.imdbcloneapi.exceptions.ApiException;
import com.andrei.plesoianu.imdbcloneapi.exceptions.NotFoundException;
import com.andrei.plesoianu.imdbcloneapi.models.Movie;
import com.andrei.plesoianu.imdbcloneapi.models.News;
import com.andrei.plesoianu.imdbcloneapi.payloads.news.CreateNewsDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.news.NewsDto;
import com.andrei.plesoianu.imdbcloneapi.repositories.MovieRepository;
import com.andrei.plesoianu.imdbcloneapi.repositories.NewsRepository;
import com.andrei.plesoianu.imdbcloneapi.services.storage.StorageService;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    private final ModelMapper modelMapper;
    private final MovieRepository movieRepository;
    private final NewsRepository newsRepository;
    private final StorageService storageService;

    public NewsServiceImpl(@NonNull ModelMapper modelMapper,
                           @NonNull MovieRepository movieRepository,
                           @NonNull NewsRepository newsRepository,
                           @NonNull StorageService storageService) {
        this.modelMapper = modelMapper;
        this.movieRepository = movieRepository;
        this.newsRepository = newsRepository;
        this.storageService = storageService;
    }

    @Override
    public NewsDto addNews(CreateNewsDto dto) {
        var movie = movieRepository.findById(dto.getMovieId())
                .orElseThrow(() -> new NotFoundException(Movie.class, dto.getMovieId()));

        var news = modelMapper.map(dto, News.class);
        news.setId(null); // get mapped from model mapper
        news.setMovie(movie);
        var createdNews = newsRepository.save(news);

        return modelMapper.map(createdNews, NewsDto.class);
    }

    @Override
    public NewsDto updateBanner(Long newsId, MultipartFile file) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new NotFoundException(News.class, newsId));

        try {
            var fileName = storageService.store(StorageType.NEWS, file);
            news.setBannerUrl(fileName);
            return modelMapper.map(newsRepository.save(news), NewsDto.class);
        } catch (IOException e) {
            throw new ApiException(e.getMessage());
        }
    }

    @Override
    public List<NewsDto> getRecentNews() {
        return newsRepository.findTop5ByOrderByAddedDesc().stream()
                .map(news -> modelMapper.map(news, NewsDto.class))
                .toList();
    }
}
