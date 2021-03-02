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

    public Movie findById(Integer id) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            final String query = "FROM Movie WHERE id = :id";
            //JOIN FETCH ma fizycznie dolaczyc zawartosc genre i country, bez przypisywania ich do obiektu Movie


            Movie movie = (Movie) session.createQuery(query)
                    .setParameter("id", id)
                    .getSingleResult();

            transaction.commit();
   //         logger.info("Object found " + movie.toString());
            return movie;
        } catch (HibernateException e) {
            logger.error(e.getMessage(), e);
            if (transaction != null)
                transaction.rollback();

        }
        return null;
    }

    public Movie findByName(String title) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String query = "FROM Movie m  WHERE m.title = :title";

            Movie movie = (Movie) session.createQuery(query)
                    .setParameter("title", title)
                    .uniqueResultOptional()
                    .orElse(null);
            transaction.commit();

  //          logger.info("Object found " + movie.toString());

            return movie;

        }catch (HibernateException e) {
            logger.error(e.getMessage(), e);
            if (transaction != null)
                transaction.rollback();

        }
        return null;
    }

    public void save(Movie movie) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(movie);

            transaction.commit();
        }catch (HibernateException e) {
            logger.error(e.getMessage(), e);
            if (transaction != null)
                transaction.rollback();

        }
    }


    public void delete(Movie movie) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(movie);
            transaction.commit();
            logger.info("succesfully deleted movie " + movie.getId());
        }catch (HibernateException e) {
            logger.error(e.getMessage(), e);
            if (transaction != null)
                transaction.rollback();

        }
    }

    public void update(Movie movie) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(movie);

            transaction.commit();
        }catch (HibernateException e) {
            logger.error(e.getMessage(), e);
            if (transaction != null)
                transaction.rollback();

        }
    }

    public List<PersonMovie> findAll() {

        return null;
    }

    public List<Movie> findAllMovies() {
        Transaction transaction = null;
        List movies = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            movies = session.createQuery("FROM Movie").getResultList();
            transaction.commit();
            return movies;
        }catch (HibernateException e) {
            logger.error(e.getMessage(), e);
            if (transaction != null)
                transaction.rollback();
        }
        return movies;
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
