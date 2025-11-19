package com.intervalo.intervalo_api.configuration;

import com.intervalo.intervalo_api.service.MovieListLoaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoadRunner implements CommandLineRunner {

    @Autowired
    private MovieListLoaderService movieListLoaderService;

    @Override
    public void run(String... args) throws Exception {
        movieListLoaderService.loadMovieList();
    }
}
