package com.sda.hibernate.commons.dao;


import com.sda.hibernate.commons.entity.Movie;
import com.sda.hibernate.commons.entity.Person;
import com.sda.hibernate.commons.entity.PersonMovie;
import org.apache.log4j.Logger;

import java.util.List;

public class MovieDao {

    private final Logger logger = Logger.getLogger(MovieDao.class);

    public Movie findById(Integer id) {
        return null;
    }

    public Movie findByName(String title) {
        return null;
    }

    public void save(Movie movie) {
    }


    public void delete(Movie movie) {
    }

    public void update(Movie movie) {
    }

    public List<PersonMovie> findAll() {
        return null;
    }

    public List<Movie> findAllMovies() {
        return null;
    }

    public List<Person> findAllActorsForMovieNativeSQL(Integer movieId) {
        return null;
    }

    public List<Person> findAllActorsForMovieHQL(Integer movieId) {
        return null;
    }

    public List<Person> findAllActorsForMovieCriteriaAPI(Integer movieId) {
        return null;
    }

    public List<Movie> findAllMoviesForActorNativeSQL(String firstName, String lastName) {
        return null;
    }

    public List<Movie> findAllMoviesForActorHQL(String firstName, String lastName) {
        return null;
    }

    public List<Movie> findAllMoviesForActorCriteriaAPI(String firstName, String lastName) {
        return null;
    }
}
