package com.andrei.plesoianu.imdbcloneapi.enums;

public enum StorageType {
    PERSON,
    NEWS,
    MOVIE;

    public String toFileName() {
        return switch(this) {
            case PERSON -> "person";
            case NEWS -> "news";
            case MOVIE -> "movie";
        };
    }
}
