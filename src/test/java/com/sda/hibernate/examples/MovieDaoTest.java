package com.sda.hibernate.examples;

import com.sda.hibernate.commons.dao.MovieDao;
import com.sda.hibernate.commons.entity.Movie;
import com.sda.hibernate.commons.entity.Person;
import com.sda.hibernate.commons.entity.PersonMovie;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class MovieDaoTest {

    private static final Logger logger = Logger.getLogger(MovieDaoTest.class);

    private MovieDao movieDao;

    public MovieDaoTest() {
        movieDao = new MovieDao();
    }

    @Test
    public void shouldFindAllMoviesWithPersons() {
        List<PersonMovie> movieList = movieDao.findAll();

        movieList.forEach(
                personMovie ->
                        logger.info(personMovie.getPerson().getLastName() + " " +
                                personMovie.getPerson().getFirstName())
        );

        Assert.assertTrue(movieList.size() > 0);
    }

    @Test
    public void shouldFindAllActorsForMovie() {
        List<Person> personList = movieDao.findAllActorsForMovie(1);

        personList.forEach(
                person -> logger.info(person.getCountry().getName())
        );

        Assert.assertTrue(personList.size() > 0);
    }

    @Test
    public void shouldShouldFindMovieById() {
        Movie foundMovie = movieDao.findById(1);

        Assert.assertEquals(foundMovie.getTitle(), "Szklana pułapka");
    }
}
