package com.andrei.plesoianu.imdbcloneapi.payloads.news;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsDto {
    private Long id;
    private String title;
    private String description;
    private String bannerUrl;
    private String buttonTitle;
    private Integer reactions;
    private Integer duration;
    private Long movieId;
}
