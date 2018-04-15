package com.alsalkini.MDApp.controller;



import com.alsalkini.MDApp.model.Actor;
import com.alsalkini.MDApp.model.Gender;
import com.alsalkini.MDApp.model.Movie;
import com.alsalkini.MDApp.model.Summary;
import com.alsalkini.MDApp.parser.ActorParser;
import com.alsalkini.MDApp.parser.MovieParser;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MovieDatabaseSummarizer {

    public Summary summarize() {
        MovieParser movieParser = new MovieParser();
        ActorParser actorParser = new ActorParser();

        List<Movie> movies = movieParser.asList("src/main/java/at/refugeescode/pset1/data/movies.csv");
        List<Actor> actors = actorParser.asList("src/main/java/at/refugeescode/pset1/data/actors.csv");

        Summary summary = new Summary();
        summary.setNumberOfMovies(movies.size());
        summary.setNumberOfActors(actors.size());
        summary.setMostRatedMovies(getMostRated(movies));
        summary.setMostHiredActors(getMostHired(actors));
        summary.setMostAppearingGenres(getMostAppearingGenres(movies));
        summary.setMaleFemaleRatio(getMaleFemaleRatio(actors));
        return summary;
    }

    private List<String> getMostRated(List<Movie> movies) {
        return movies.stream()
                .sorted((e1, e2) -> e2.getRating().compareTo(e1.getRating()))
                .limit(5)
                .map(e -> e.getTitle() + " (" + e.getRating() + ")")
                .collect(Collectors.toList());
    }

    private List<String> getMostHired(List<Actor> actors) {
        return actors.stream()
                .map(actor -> actor.getName())
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()))
                .entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(5)
                .map(e -> e.getKey() + " (" + e.getValue() + ")")
                .collect(Collectors.toList());
    }

    private List<String> getMostAppearingGenres(List<Movie> movies) {
        return movies.stream()
                .map(movie -> movie.getGenres())
                .flatMap(genres -> genres.stream())
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()))
                .entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(2)
                .map(e -> e.getKey() + " (" + e.getValue() + ")")
                .collect(Collectors.toList());
    }

    private String getMaleFemaleRatio(List<Actor> actors) {
        double size = actors.size();
        return actors.stream()
                .map(actor -> actor.getGender())
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()))
                .entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .map(e -> getPercentage(size, e) + e.getKey().toString().toLowerCase())
                .collect(Collectors.joining(", "));
    }

    private String getPercentage(double size, Map.Entry<Gender, Long> e) {
        double percentage = e.getValue() / size;
        NumberFormat formatter = new DecimalFormat("0.0 %");
        return formatter.format(percentage);
    }
}
