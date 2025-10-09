package com.andrei.plesoianu.imdbcloneapi.services.parser;

import com.andrei.plesoianu.imdbcloneapi.enums.MovieRole;
import com.andrei.plesoianu.imdbcloneapi.enums.MovieType;
import com.andrei.plesoianu.imdbcloneapi.payloads.character.CharacterDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.contributor.ContributorDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.genre.GenreDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.movie.ExtendedCreateMovieDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.person.PersonDto;
import com.andrei.plesoianu.imdbcloneapi.payloads.season.CreateSeasonDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.parser.StreamParser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ParserServiceImpl implements ParserService {
    @Override
    public void parseMovieUrl(String url) throws IOException {
        var extractedData = new ExtendedCreateMovieDto();

        try (StreamParser streamer = Jsoup.connect(url)
                .execute()
                .streamParser()) {

            Element el;

            if (((el = streamer.selectNext("span.hero__primary-text")) != null)) {
                extractedData.setTitle(el.text().trim());
                el.remove();
            }

            if (((el = streamer.selectNext("ul.ipc-inline-list")) != null)) {
                Element firstChild = el.child(0);
                if (firstChild.text().trim().equals("TV Series")) {
                    extractedData.setType(MovieType.SERIES);
                } else {
                    extractedData.setType(MovieType.MOVIE);
                }
                firstChild.remove();

                Element ageRestrictionEl = extractedData.getType() == MovieType.MOVIE ?
                        el.child(0) : el.child(1);
                extractedData.setAgeRestriction(ageRestrictionEl.text().trim());
                ageRestrictionEl.remove();

                el.remove();
            }

            int counter = 0;
            extractedData.setGenres(new ArrayList<>());
            while ((el = streamer.selectNext("div[data-testid=interests] span.ipc-chip__text")) != null) {
                var genre = new GenreDto();
                genre.setName(el.text().trim());
                extractedData.getGenres().add(genre);
                el.remove();
                if (++counter == 5) {
                    break;
                }
            }

            if (((el = streamer.selectNext("p[data-testid=plot]")) != null)) {
                Element secondChild = el.child(1);
                extractedData.setOverview(secondChild.text().trim());
                el.remove();
            }

            extractedData.setContributors(new ArrayList<>());
            if ((el = streamer.selectNext("ul[data-testid=title-pc-list]")) != null) {
                el.selectStream("li[data-testid=title-pc-principal-credit]")
                        .forEach(f -> {
                            var type = f.child(0).text().trim();
                            if (!type.equalsIgnoreCase("Stars")) {
                                var parsedType = type.endsWith("s") ?
                                        type.substring(0, type.length() - 1) : type;

                                f.child(1).selectStream("li").forEach(f1 -> {
                                    var person = new PersonDto();
                                    person.setName(f1.text().trim());

                                    var contributor = new ContributorDto();
                                    contributor.setPerson(person);
                                    try {
                                        contributor.setRole(MovieRole.valueOf(parsedType.toUpperCase()));
                                        extractedData.getContributors().add(contributor);
                                    } catch (IllegalArgumentException ignored) {}
                                });
                            }
                        });
                el.remove();
            }

            if (extractedData.getType() == MovieType.SERIES) {
                if ((el = streamer.selectNext("div[data-testid=episodes-header]")) != null) {
                    var firstChild = el.firstChild();
                    if (firstChild != null && firstChild.hasAttr("href")) {
                        var seasonsLink = firstChild.attr("href");
                        extractedData.setSeasons(parseSeasonLinks("https://www.imdb.com" + seasonsLink).stream()
                                .map(this::parseSeason)
                                .toList());
                    }
                    el.remove();
                }
            }

            counter = 0;
            extractedData.setCharacters(new ArrayList<>());
            while ((el = streamer.selectNext("div[data-testid=title-cast-item]")) != null) {
                var person = new PersonDto();
                var character = new CharacterDto();

                var secondChild = el.child(1);
                person.setName(secondChild.child(0).text().trim());
                character.setName(secondChild.child(1).child(0).child(0).child(0).text().trim());

                character.setActor(person);
                extractedData.getCharacters().add(character);

                secondChild.remove();
                el.remove();
                if (++counter == 5) {
                    break;
                }
            }

            if (extractedData.getType() == MovieType.MOVIE) {
                if ((el = streamer.selectNext("li[data-testid=title-details-releasedate]")) != null) {
                    var rawDate = el.child(1).text().trim();
                    if (rawDate.contains("(")) {
                        rawDate = rawDate.substring(0, rawDate.indexOf("(")).trim();
                    }
                    extractedData.setReleaseDate(parseDate(rawDate));
                }
            }
        }
    }

    private CreateSeasonDto parseSeason(String url) throws UncheckedIOException {
        var extractedSeason = new CreateSeasonDto();

        try (StreamParser streamer = Jsoup.connect(url)
                .execute()
                .streamParser()) {

            Element el;

            int count = 0;
            while ((el = streamer.selectNext("article.episode-item-wrapper")) != null) {
                if (count == 0) {
                    var titleEl = el.selectFirst("h4[data-testid=slate-list-card-title]");
                    if (titleEl != null) {
                        var releaseDateEl = titleEl.nextElementSibling();
                        if (releaseDateEl != null) {
                            extractedSeason.setReleaseDate(parseDate(releaseDateEl.text().trim()));
                        }
                    }
                }

                count++;
                el.remove();
            }

            extractedSeason.setEpisodes(count);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return extractedSeason;
    }

    private List<String> parseSeasonLinks(String url) throws IOException {
        List<String> seasonLinks = new ArrayList<>();

        try (StreamParser streamer = Jsoup.connect(url)
                .execute()
                .streamParser()) {
            Element el;

            while ((el = streamer.selectNext("a[data-testid=tab-season-entry]")) != null) {
                if (el.hasAttr("href")) {
                    seasonLinks.add("https://www.imdb.com" + el.attr("href"));
                }
                el.remove();
            }
        }

        return seasonLinks;
    }

    private LocalDate parseDate(String rawDate) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            // optional weekday
            .optionalStart().appendPattern("EEE, ").optionalEnd()
            // full date pattern
            .optionalStart().appendPattern("MMM d, yyyy").optionalEnd()
            // just year
            .optionalStart().appendPattern("yyyy")
            .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
            .optionalEnd()
            .toFormatter(Locale.ENGLISH);

        try {
            return LocalDate.parse(rawDate.trim(), formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
