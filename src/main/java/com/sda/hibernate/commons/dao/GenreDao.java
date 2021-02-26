package com.sda.hibernate.commons.dao;

import com.sda.hibernate.commons.connection.HibernateUtil;
import com.sda.hibernate.commons.entity.Genre;
import lombok.extern.log4j.Log4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

@Log4j
public class GenreDao {

    public List<Genre> findAll() {
        Transaction transaction = null;
        List<Genre> genres = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            genres = session.createQuery("FROM Genre ").getResultList();
            transaction.commit();
        } catch (HibernateException hibernateException) {
            transaction.rollback();
            log.error(hibernateException.getMessage());
        }

        return genres;
    }

    public void saveOrUpdate(Genre genre) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(genre);
            transaction.commit();
        } catch (HibernateException hibernateException) {
            transaction.rollback();
            log.error(hibernateException.getMessage());
        }
    }

    public void delete(Genre genre) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(genre);
            transaction.commit();
        } catch (HibernateException hibernateException) {
            log.error(hibernateException.getMessage());
            transaction.rollback();
        }
    }

    public Genre findById(int id) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Genre genre = (Genre) session.createQuery("FROM Genre WHERE id = :id")
                    .setParameter("id", id)
                    .uniqueResultOptional().orElse(null);

            transaction.commit();

            return genre;
        } catch (HibernateException hibernateException) {
            log.error(hibernateException.getMessage());
        }

        return null;
    }
}
