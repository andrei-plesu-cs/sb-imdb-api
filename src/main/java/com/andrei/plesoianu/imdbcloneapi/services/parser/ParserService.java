package com.andrei.plesoianu.imdbcloneapi.services.parser;

import java.io.IOException;

public interface ParserService {
    void parseMovieUrl(String url) throws IOException;
}
