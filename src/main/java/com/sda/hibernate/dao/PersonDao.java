package com.sda.hibernate.dao;

import com.sda.hibernate.connection.HibernateUtil;
import com.sda.hibernate.entity.Person;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class PersonDao {

    private final Logger logger = Logger.getLogger(PersonDao.class);

    public void saveOrUpdate(Person person) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(person);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            logger.error(e.getMessage(), e);
        }
    }


    public void update(Person person) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(person);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            logger.error(e.getMessage(), e);
        }
    }


    public void delete(Person person) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(person);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            logger.error(e.getMessage(), e);
        }
    }

    public List<Person> findAll() {
        Transaction transaction = null;
        List<Person> personList = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // dla sprawdzenia SELECT n+1 problem
//            personList =
//                    session.createQuery("SELECT p FROM Person p").getResultList();

            personList =
                    session.createQuery("SELECT p FROM Person p JOIN FETCH p.country").getResultList();


            transaction.commit();

            return personList;
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            logger.error(e.getMessage(), e);
        }

        return personList;
    }

    public Person findById(Integer id) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Person person = session.get(Person.class, id);

            transaction.commit();

            return person;
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            logger.error(e.getMessage(), e);
        }

        return null;
    }

    public Person findByFirstAndLastName(String firstName, String lastName) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Person person = (Person) session.createQuery("FROM Person p " +
                    "WHERE p.firstName = :firstName AND p.lastName = :lastName").
                    setParameter("firstName", firstName).
                    setParameter("lastName", lastName).
                    getSingleResult();

            transaction.commit();

            return person;
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            logger.error(e.getMessage(), e);
        }

        return null;
    }
}
