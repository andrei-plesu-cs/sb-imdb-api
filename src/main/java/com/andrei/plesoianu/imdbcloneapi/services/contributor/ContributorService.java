package com.andrei.plesoianu.imdbcloneapi.services.contributor;

import com.andrei.plesoianu.imdbcloneapi.payloads.contributor.ContributorDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.contributor.CreateContributorDto;

public interface ContributorService {
    ContributorDto addContributor(Long movieId, CreateContributorDto dto);
}
