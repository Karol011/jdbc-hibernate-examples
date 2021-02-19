package com.sda.hibernate.todo;

import com.sda.hibernate.commons.dao.MovieDao;
import com.sda.hibernate.commons.entity.Country;
import com.sda.hibernate.commons.entity.Movie;
import com.sda.hibernate.commons.entity.Person;
import lombok.extern.log4j.Log4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.NoResultException;
import java.util.List;

@Log4j
public class ZadaniaHibernate {

    private MovieDao movieDao;

    public ZadaniaHibernate() {
        this.movieDao = new MovieDao();
    }

    @Test(testName = "zad. 1 - Wyszukaj film po identyfikatorze")
    public void shouldFindMovieById() {
        Movie foundMovie = movieDao.findById(1);

        Assert.assertEquals(foundMovie.getTitle(), "Szklana pułapka");
    }

    @Test(testName = "zad. 2 - Dodaj nowy film")
    public void shouldSaveMovie() {
        Movie movie = new Movie();
        movie.setTitle("Sprawa się rypła");
        movie.setDuration(120);
        movie.setProductionYear(1980);

        movieDao.save(movie);

        Movie savedMovie = movieDao.findByName("Sprawa się rypła");

        Assert.assertEquals(savedMovie.getTitle(), "Sprawa się rypła");
    }

    @Test(testName = "zad. 3 - Usuń film na podstawie dowolnych parametrów, np. identyfikatora")
    public void shouldDeleteMovie() {
        Movie movieBeforeDelete = movieDao.findByName("Sprawa się rypła");

        movieDao.delete(movieBeforeDelete);

        Movie movieAfterDelete = movieDao.findByName("Sprawa się rypła");

        Assert.assertNotNull(movieBeforeDelete);
    }

    @Test(testName = "zad. 4 - Zmień dane o filmie np. tytuł")
    public void shouldUpdateMovieTitle() {
        Movie movieToUpdate = movieDao.findByName("Sprawa się rypła");
        String oldTitle = movieToUpdate.getTitle();

        movieDao.update(movieToUpdate);

        Movie movieAfterUpdate = movieDao.findByName("Sprawa się rypła po raz kolejny");

        Assert.assertNotNull(movieToUpdate);
    }

    @Test(testName = "zad. 5 - Znajdź wszystkie filmy")
    public void shouldFindAllMovies() {
        List<Movie> allMovies = movieDao.findAllMovies();

        Assert.assertFalse(allMovies.isEmpty());
    }

    @Test(testName = "zad. 6 - (użyj NativeSQL)  Znajdź dane wszystkich aktorów występujących w filmie, np. na podstawie ID filmu")
    public void shouldFindAllActorsForMovieWithNativeSQL() {
        List<Person> personList = movieDao.findAllActorsForMovieNativeSQL(1);

//        personList.forEach(
//                person -> log.info(person.getCountry().getName())
//        );

        Assert.assertTrue(personList.size() > 0);
    }

    @Test(testName = "zad. 7 - (użyj HQL)  Znajdź dane wszystkich aktorów występujących w filmie, np. na podstawie ID filmu")
    public void shouldFindAllActorsForMovieWithHQL() {
        List<Person> personList = movieDao.findAllActorsForMovieHQL(1);

        personList.forEach(
                person -> log.info(person.getCountry().getName())
        );

        Assert.assertTrue(personList.size() > 0);
    }

    @Test(testName = "zad. 8 - (użyj Criteria API)  Znajdź dane wszystkich aktorów występujących w filmie, np. na podstawie ID filmu")
    public void shouldFindAllActorsForMovieWithCriteria() {
        List<Person> personList = movieDao.findAllActorsForMovieCriteriaAPI(1);

        personList.forEach(
                person -> log.info(person.getCountry().getName())
        );

        Assert.assertTrue(personList.size() > 0);
    }
    @Test(testName = "zad. 9 - (użyj NativeSQL) Znajdź wszystkie filmy w których wystąpił aktor o podanych parametrach, np. imię i nazwisko")
    public void shouldFindAllMoviesForActorWithNativeSQL() {
        List<Movie> result = movieDao.findAllMoviesForActorNativeSQL("Leo", "Messi");

        Assert.assertNotNull(result);
    }

    @Test(testName = "zad. 10 - (użyj HQL) Znajdź wszystkie filmy w których wystąpił aktor o podanych parametrach, np. imię i nazwisko")
    public void shouldFindAllMoviesForActorWithHQL() {
        List<Movie> result = movieDao.findAllMoviesForActorHQL("Leo", "Messi");

        Assert.assertNotNull(result);
    }

    @Test(testName = "zad. 11 - (użyj Criteria API) Znajdź wszystkie filmy w których wystąpił aktor o podanych parametrach, np. imię i nazwisko")
    public void shouldFindAllMoviesForActorWithCriteriaAPI() {
        List<Movie> result = movieDao.findAllMoviesForActorCriteriaAPI("Leo", "Messi");

        Assert.assertNotNull(result);
    }

    @Test(testName = "zad. 12 - Zapisz z wykorzystaniem opcji cascade dane o filmie wraz z osobami, które brały przy nim udział")
    public void shouldSaveCascadeDataAboutMovieAndPersons() {
        Movie existingMovie = movieDao.findById(150);

        if(existingMovie != null)
            movieDao.delete(existingMovie);

        Movie movie = new Movie();
        movie.setId(150);
        movie.setTitle("FC Barcelona - PSG");
        movie.setCountry(new Country(100, "Zimbabwe"));

        movieDao.save(movie);

        Movie savedMovie = movieDao.findById(150);

        movieDao.delete(savedMovie);

        Movie deletedMovie = movieDao.findById(150);

        Assert.assertNotNull(savedMovie);
        Assert.assertNull(deletedMovie);
    }
}
