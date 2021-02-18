package com.sda.hibernate.commons.dao;


import com.sda.hibernate.commons.connection.HibernateUtil;
import com.sda.hibernate.commons.entity.Movie;
import com.sda.hibernate.commons.entity.Person;
import com.sda.hibernate.commons.entity.PersonMovie;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieDao {

    private final Logger logger = Logger.getLogger(MovieDao.class);

    public Movie findById(Integer id) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Movie movie = session.get(Movie.class, id);

            transaction.commit();

            return movie;
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            logger.error(e.getMessage(), e);
        }

        return null;
    }

    public Movie findByName(String title) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Movie movie = (Movie) session.createQuery("FROM Movie m " +
                    "WHERE m.title = :title").
                    setParameter("title", title).
                    getSingleResult();

            transaction.commit();

            return movie;
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            logger.error(e.getMessage(), e);
        }

        return null;
    }

    public void save(Movie movie) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(movie);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            logger.error(e.getMessage(), e);
        }
    }


    public void delete(Movie movie) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(movie);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            logger.error(e.getMessage(), e);
        }
    }

    public List<PersonMovie> findAll() {
        Transaction transaction = null;
        List<PersonMovie> personMovies = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            personMovies =
                    session.createQuery("SELECT m FROM PersonMovie m " +
                            "LEFT JOIN FETCH m.person " +
                            "LEFT JOIN FETCH m.movie " +
                            "LEFT JOIN FETCH m.personType").
                            getResultList();

            transaction.commit();

            return personMovies;
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            logger.error(e.getMessage(), e);
        }

        return personMovies;
    }

    public List<Movie> findAllMovies() {
        List<Movie> movies = new ArrayList<>();
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            movies = session.createQuery("SELECT m FROM Movie m").getResultList();

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            logger.error(e.getMessage(), e);
        }

        return movies;
    }

    public List<Person> findAllActorsForMovieNativeSQL(Integer movieId) {
        Transaction transaction = null;
        List<Person> personMovies = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // NATIVE SQL
            personMovies =
                    session.createNativeQuery("SELECT p.*, c.* FROM persons_movies pm " +
                            "JOIN persons p ON pm.person_id = p.person_id " +
                            "JOIN countries c ON p.country_id = c.country_id " +
                            "WHERE pm.movie_id = :movieId AND pm.type_id = 1", Person.class).
                            setParameter("movieId", movieId).
                            getResultList();

            transaction.commit();

            return personMovies;
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            logger.error(e.getMessage(), e);
        }

        return personMovies;
    }

    public List<Person> findAllActorsForMovieHQL(Integer movieId) {
        Transaction transaction = null;
        List<Person> personMovies = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // HQL
            personMovies =
                    session.createQuery("SELECT m.person FROM PersonMovie m " +
                            "JOIN m.person " +
                            "JOIN FETCH m.person.country c " +
                            "WHERE m.movieId = :movieId AND m.typeId = 1").
                            setParameter("movieId", movieId).
                            getResultList();

            transaction.commit();

            return personMovies;
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            logger.error(e.getMessage(), e);
        }

        return personMovies;
    }

    public List<Person> findAllActorsForMovieCriteriaAPI(Integer movieId) {
        Transaction transaction = null;
        List<Person> actors = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<PersonMovie> cr = cb.createQuery(PersonMovie.class);

            Root<PersonMovie> root = cr.from(PersonMovie.class);
            root.fetch("person", JoinType.INNER).fetch("country", JoinType.INNER);
            root.fetch("personType", JoinType.INNER);

            Predicate predicateForMovieId = cb.equal(root.get("movieId"), movieId);
            Predicate predicateForTypeName = cb.like(root.get("personType").get("name"), "actor");

            cr.where(cb.and(predicateForMovieId, predicateForTypeName));
            cr.select(root);

            Query<PersonMovie> query = session.createQuery(cr);
            List<PersonMovie> results = query.getResultList();

            actors = results.stream().map(PersonMovie::getPerson).collect(Collectors.toList());

            transaction.commit();

            return actors;
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            logger.error(e.getMessage(), e);
        }

        return actors;
    }

    public List<Movie> findAllMoviesForActorNativeSQL(String firstName, String lastName) {
        Transaction transaction = null;
        List<Movie> movies = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // NATIVE SQL
            movies =
                    session.createNativeQuery("SELECT m.* FROM persons_movies pm " +
                            "JOIN persons p ON pm.person_id = p.person_id " +
                            "JOIN movies m ON pm.movie_id = m.movie_id " +
                            "WHERE p.first_name = :firstName AND p.last_name = :lastName AND pm.type_id = 1", Movie.class).
                            setParameter("firstName", firstName).
                            setParameter("lastName", lastName).
                            getResultList();

            transaction.commit();

            return movies;
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            logger.error(e.getMessage(), e);
        }

        return movies;
    }

    public List<Movie> findAllMoviesForActorHQL(String firstName, String lastName) {
        Transaction transaction = null;
        List<Movie> movies = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // HQL
            movies =
                    session.createQuery("SELECT movie FROM PersonMovie m " +
                            "JOIN m.person p " +
                            "JOIN m.movie movie " +
                            "WHERE p.firstName = :firstName AND p.lastName = :lastName AND m.typeId = 1").
                            setParameter("firstName", firstName).
                            setParameter("lastName", lastName).
                            getResultList();

            transaction.commit();

            return movies;
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            logger.error(e.getMessage(), e);
        }

        return movies;
    }

    public List<Movie> findAllMoviesForActorCriteriaAPI(String firstName, String lastName) {
        Transaction transaction = null;
        List<Movie> movies = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<PersonMovie> cr = cb.createQuery(PersonMovie.class);

            Root<PersonMovie> root = cr.from(PersonMovie.class);
            root.join("person", JoinType.INNER);
            root.fetch("movie", JoinType.INNER);
            root.join("personType", JoinType.INNER);

            Predicate predicateForTypeId = cb.equal(root.get("personType").get("id"), 1);
            Predicate predicateForFirstName = cb.equal(root.get("person").get("firstName"), firstName);
            Predicate predicateForLastName = cb.equal(root.get("person").get("lastName"), lastName);

            cr.where(cb.and(predicateForTypeId, predicateForFirstName, predicateForLastName));
            cr.select(root.get("movie"));

            Query<PersonMovie> query = session.createQuery(cr);

            movies = query.getResultList().stream()
                    .map(PersonMovie::getMovie)
                    .collect(Collectors.toList());

            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            logger.error(e.getMessage(), e);
        }

        return movies;
    }
}
