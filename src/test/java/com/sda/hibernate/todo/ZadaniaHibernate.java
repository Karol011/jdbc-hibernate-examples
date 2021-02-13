package com.sda.hibernate.todo;

import com.sda.hibernate.commons.dao.MovieDao;
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

    @Test(testName = "zad. 3 - Usuń film na podstawie dowolnych parametrów, np. identyfikatora",
            dependsOnMethods = "shouldSaveMovie",
            expectedExceptions = NoResultException.class)
    public void shouldDeleteMovie() {
        Movie movieBeforeDelete = movieDao.findByName("Sprawa się rypła");

        movieDao.delete(movieBeforeDelete);

        Movie movieAfterDelete = movieDao.findByName("Sprawa się rypła");

        Assert.assertNull(movieAfterDelete);
    }

    @Test(testName = "zad. 4 - Zmień dane o filmie np. tytuł")
    public void shouldUpdateMovieTitle() {
//        Movie movieBeforeDelete = movieDao.findByName("Sprawa się rypła");
//
//        movieDao.delete(movieBeforeDelete);
//
//        Movie movieAfterDelete = movieDao.findByName("Sprawa się rypła");
//
//        Assert.assertNull(movieAfterDelete);
    }

    @Test(testName = "zad. 5 - Znajdź wszystkie filmy")
    public void shouldFindAllMovies() {
        List<Movie> allMovies = movieDao.findAllMovies();

        Assert.assertFalse(allMovies.isEmpty());
    }

    @Test(testName = "zad. 6 - (użyj HQL)  Znajdź dane wszystkich aktorów występujących w filmie, np. na podstawie ID filmu")
    public void shouldFindAllActorsForMovieWithHQL() {
        List<Person> personList = movieDao.findAllActorsForMovieHQL(1);

        personList.forEach(
                person -> log.info(person.getCountry().getName())
        );

        Assert.assertTrue(personList.size() > 0);
    }

    @Test(testName = "zad. 7 - (użyj Criteria API)  Znajdź dane wszystkich aktorów występujących w filmie, np. na podstawie ID filmu")
    public void shouldFindAllActorsForMovieWithCriteria() {
        List<Person> personList = movieDao.findAllActorsForMovieCriteriaAPI(1);

        personList.forEach(
                person -> log.info(person.getCountry().getName())
        );

        Assert.assertTrue(personList.size() > 0);
    }

    @Test(testName = "zad. 8 - (użyj HQL) Znajdź wszystkie filmy w których wystąpił aktor o podanych parametrach, np. imię i nazwisko")
    public void shouldFindAllMoviesForActorWithHQL() {
        List<Movie> result = movieDao.findAllMoviesForActorHQL("Imię", "Nazwisko");

        Assert.assertNotNull(result);
    }

    @Test(testName = "zad. 9 - (użyj Criteria API) Znajdź wszystkie filmy w których wystąpił aktor o podanych parametrach, np. imię i nazwisko")
    public void shouldFindAllMoviesForActorWithCriteriaAPI() {
        List<Movie> result = movieDao.findAllMoviesForActorCriteriaAPI("Imię", "Nazwisko");

        Assert.assertNotNull(result);
    }

    @Test(testName = "zad. 10 - Zapisz z wykorzystaniem opcji cascade dane o filmie wraz z osobami, które brały przy nim udział")
    public void shouldSaveCascadeDataAboutMovieAndPersons() {
    }
}
