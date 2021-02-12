package com.sda.hibernate.commons.dao;

import com.sda.hibernate.commons.connection.HibernateUtil;
import com.sda.hibernate.commons.entity.Country;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;

import java.util.ArrayList;
import java.util.List;

public class CountryDao {

    private final Logger logger = Logger.getLogger(CountryDao.class);

    public void save(Country Country) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(Country);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            logger.error(e.getMessage(), e);
        }
    }


    public void update(Country Country) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(Country);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            logger.error(e.getMessage(), e);
        }
    }

    public List<Country> findAll() {
        Transaction transaction = null;
        List<Country> countries = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            countries =
                    session.createQuery("FROM Country ").getResultList();

            transaction.commit();

            return countries;
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            logger.error(e.getMessage(), e);
        }

        return countries;
    }

    public Country findById(Integer id) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Country country = session.get(Country.class, id);

            transaction.commit();

            return country;
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            logger.error(e.getMessage(), e);
        }

        return null;
    }

    public Country findByName(String name) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Country country = (Country) session.createQuery("FROM Country WHERE name = :name").
                    setParameter("name", name).getSingleResult();

            transaction.commit();

            return country;
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            logger.error(e.getMessage(), e);
        }

        return null;
    }


    public void delete(Country country) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.delete(country);

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            logger.error(e.getMessage(), e);
        }
    }

    public void shouldDisplayHistoryForRecord() {
        Transaction transaction = null;

        List<Country> countries = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            countries = AuditReaderFactory.get(session.getSession().getEntityManagerFactory().createEntityManager())
                    .createQuery()
                    .forRevisionsOfEntity(Country.class, true, true)
                    .add(AuditEntity.id().eq(1))
                    .getResultList();

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            logger.error(e.getMessage(), e);
        }

        countries.forEach(
                Country::toString
        );
    }
}
