package com.sda.hibernate.examples;

import com.sda.hibernate.commons.dao.GenreDao;
import com.sda.hibernate.commons.entity.Genre;
import lombok.extern.log4j.Log4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

@Log4j
public class GenreDaoTest {

    private GenreDao genreDao;

    public GenreDaoTest() {
        this.genreDao = new GenreDao();
    }

    @Test
    public void shouldFindAllGenres() {
        List<Genre> genres = genreDao.findAll();

        Assert.assertFalse(genres.isEmpty());
    }

    @Test
    public void shouldSaveNewGenre() {
        Genre toSave = new Genre();
        toSave.setId(99);
        toSave.setName("Komedia");

        genreDao.saveOrUpdate(toSave);

        Genre afterSave = genreDao.findById(99);

        log.info("After save: " + afterSave);
        genreDao.delete(afterSave);

        Genre afterDelete = genreDao.findById(99);

        Assert.assertNotNull(afterSave);
        Assert.assertNull(afterDelete);
    }

    @Test
    public void shouldUpdateGenre() {
        String newName = "Zmieniona komedia";

        Genre toSave = new Genre();
        toSave.setId(99);
        toSave.setName("Komedia");

        genreDao.saveOrUpdate(toSave);

        Genre afterSave = genreDao.findById(99);
        log.info("After save: " + afterSave);

        afterSave.setName("Zmieniona komedia");

        genreDao.saveOrUpdate(afterSave);

        Genre afterUpdate = genreDao.findById(99);
        log.info("After update: " + afterUpdate);

        genreDao.delete(afterUpdate);

        Genre afterDelete = genreDao.findById(99);

        Assert.assertNotNull(afterSave);
        Assert.assertEquals(afterUpdate.getName(), newName);
        Assert.assertNull(afterDelete);
    }
}
