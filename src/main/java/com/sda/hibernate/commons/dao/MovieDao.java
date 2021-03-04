package com.sda.hibernate.commons.dao;


import com.sda.hibernate.commons.connection.HibernateUtil;
import com.sda.hibernate.commons.entity.Movie;
import com.sda.hibernate.commons.entity.Person;
import com.sda.hibernate.commons.entity.PersonMovie;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Persistence;
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

        } catch (HibernateException e) {
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
        } catch (HibernateException e) {
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
        } catch (HibernateException e) {
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
        } catch (HibernateException e) {
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
        } catch (HibernateException e) {
            logger.error(e.getMessage(), e);
            if (transaction != null)
                transaction.rollback();
        }
        return movies;
    }

    public List<Person> findAllActorsForMovieNativeSQL(Integer movieId) {
        final String ACTOR_ID = "1";
        String query = "SELECT " +
                "    persons.person_id," +
                "    persons.first_name," +
                "    persons.last_name," +
                "    movies.title " +
                "    person_types.type_id " +
                " FROM " +
                "    persons" +
                "        JOIN" +
                "    persons_movies ON persons_movies.person_id = persons.person_id" +
                "JOIN" +
                "    movies ON movies.movie_id = persons_movies.movie_id" +
                "JOIN person_types on persons.person_id = persons_movies.type_id " +
                "WHERE" +
                "    person_types.type_id = 1 " +
                "AND " +
                " movies.movie_id = :id";


        Transaction transaction = null;
        List<Person> actors = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

          /*  actors = session.createNativeQuery(query, Person.class)
                    .setParameter("id", movieId)
                   // .addEntity(Person.class)
                    .getResultList();
*/
            actors =
                    session.createNativeQuery("SELECT p.*, c.* FROM persons_movies pm " +
                            "JOIN persons p ON pm.person_id = p.person_id " +
                            "JOIN countries c ON p.country_id = c.country_id " +
                            "WHERE pm.movie_id = :movieId AND pm.type_id = 1", Person.class).
                            setParameter("movieId", movieId).
                            getResultList();
            transaction.commit();
            return actors;
        } catch (HibernateException e) {
            logger.error(e.getMessage(), e);
            if (transaction != null)
                transaction.rollback();
        }
        return actors;
    }

    //użyj HQL)  Znajdź dane wszystkich aktorów występujących w filmie, np. na podstawie ID filmu")
    public List<Person> findAllActorsForMovieHQL(Integer movieId) {
        Transaction transaction = null;
        List<Person> actors = new ArrayList<>();
        String query = "SELECT pm.person FROM PersonMovie pm " +
                "JOIN FETCH Movie m " +
                "JOIN FETCH Person p " +
                "WHERE movie_id = :id ";


        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            actors = session.createQuery(query)
                    .setParameter("id", movieId)
                    .getResultList();
            transaction.commit();
            return actors;
        } catch (HibernateException e) {
            logger.error(e.getMessage(), e);
            if (transaction != null)
                transaction.rollback();
        }
        return actors;
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
