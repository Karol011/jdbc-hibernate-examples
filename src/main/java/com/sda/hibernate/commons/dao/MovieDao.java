package com.sda.hibernate.commons.dao;


import com.sda.hibernate.commons.connection.HibernateUtil;
import com.sda.hibernate.commons.entity.Movie;
import com.sda.hibernate.commons.entity.Person;
import com.sda.hibernate.commons.entity.PersonMovie;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class MovieDao {

    private final Logger logger = Logger.getLogger(MovieDao.class);

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

            // NATIVE SQL
//            personMovies =
//                    session.createNativeQuery("SELECT p.* FROM persons_movies pm " +
//                            "JOIN persons p ON pm.person_id = p.person_id " +
//                            "WHERE pm.movie_id = :movieId AND pm.type_id = 1", Person.class).
//                            setParameter("movieId", movieId).
//                            getResultList();

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
        return null;
    }

    public List<Movie> findAllMoviesForActorHQL(String firstName, String lastName) {
        return null;
    }

    public List<Movie> findAllMoviesForActorCriteriaAPI(String firstName, String lastName) {
        return null;
    }

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
}
