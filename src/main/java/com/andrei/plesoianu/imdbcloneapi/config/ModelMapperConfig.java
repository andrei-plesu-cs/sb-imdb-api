package com.andrei.plesoianu.imdbcloneapi.config;

import com.andrei.plesoianu.imdbcloneapi.enums.StorageType;
import com.andrei.plesoianu.imdbcloneapi.models.Actor;
import com.andrei.plesoianu.imdbcloneapi.models.Movie;
import com.andrei.plesoianu.imdbcloneapi.models.News;
import com.andrei.plesoianu.imdbcloneapi.payloads.actor.ActorDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.character.CharacterDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.movie.CompactMovieDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.movie.MovieDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.news.NewsDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Value("${app.storage.basefilename}")
    private String baseFilePath;

    @Bean
    ModelMapper modelMapper() {
        var mapper = new ModelMapper();

        mapper.createTypeMap(Actor.class, ActorDto.class)
                .addMappings(m -> m.skip(ActorDto::setProfileImageUrl))
                .setPostConverter(context -> {
                    Actor source = context.getSource();
                    ActorDto destination = context.getDestination();

                    if (source.getProfileImageUrl() != null) {
                        destination.setProfileImageUrl(
                                String.format("%s/%s/%s", baseFilePath, StorageType.PERSON.toFileName(), source.getProfileImageUrl())
                        );
                    }

                    return destination;
                });

        mapper.createTypeMap(Movie.class, CompactMovieDto.class)
                .addMappings(m -> m.skip(CompactMovieDto::setPosterUrl))
                .setPostConverter(context -> {
                    Movie source = context.getSource();
                    CompactMovieDto destination = context.getDestination();

                    if (source.getPosterUrl() != null) {
                        destination.setPosterUrl(
                                String.format("%s/%s/%s", baseFilePath, StorageType.MOVIE.toFileName(), source.getPosterUrl())
                        );
                    }

                    return destination;
                });

        mapper.createTypeMap(Movie.class, MovieDto.class)
                .setPostConverter(context -> {
                    Movie source = context.getSource();
                    MovieDto destination = context.getDestination();

                    destination.setCharacters(source.getCharacters().stream()
                            .map(character -> {
                                var dto = mapper.map(character, CharacterDto.class);
                                dto.setActor(mapper.map(character.getActor(), ActorDto.class));
                                return dto;
                            })
                            .toList());

                    if (source.getPosterUrl() != null) {
                        destination.setPosterUrl(
                                String.format("%s/%s/%s", baseFilePath, StorageType.MOVIE.toFileName(), source.getPosterUrl())
                        );
                    }

                    return destination;
                });

        mapper.createTypeMap(News.class, NewsDto.class)
                .addMappings(m -> m.skip(NewsDto::setBannerUrl))
                .setPostConverter(context -> {
                    News source = context.getSource();
                    NewsDto destination = context.getDestination();

                    if (source.getBannerUrl() != null) {
                        destination.setBannerUrl(
                                String.format("%s/%s/%s", baseFilePath, StorageType.NEWS.toFileName(), source.getBannerUrl())
                        );
                    }

                    return destination;
                });

        return mapper;
    }
}
