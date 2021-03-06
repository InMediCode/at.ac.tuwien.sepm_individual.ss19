package at.ac.tuwien.sepm.assignment.individual.e1207708.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Jockey;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.e1207708.persistence.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.e1207708.persistence.util.DBConnectionManager;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class JockeyDaoTest {

    @Autowired
    IJockeyDao jockeyDao;
    @Autowired
    DBConnectionManager dbConnectionManager;

    /**
     * It is important to close the database connection after each test in order to clean the in-memory database
     */
    @After
    public void afterEachTest() throws PersistenceException {
        dbConnectionManager.closeConnection();
    }

    @Test(expected = NotFoundException.class)
    public void givenNothing_whenFindJockeyByIdWhichNotExists_thenNotFoundException()
        throws PersistenceException, NotFoundException {
        jockeyDao.findOneById(1);
    }

    @Test
    public void insertJockeyTest() throws PersistenceException {
        Jockey jockey = new Jockey(null, "JockeyName 1", 23.1, null, null);
        Jockey newJockey = jockeyDao.insertJockey(jockey);
        assertEquals(jockey.getName(), newJockey.getName());
        assertEquals(jockey.getSkill(), newJockey.getSkill());
        assertNotNull(newJockey.getId());
        assertNotNull(newJockey.getCreated());
        assertNotNull(newJockey.getUpdated());
    }

    @Test
    public void updateJockeyTest() throws PersistenceException, NotFoundException {
        Jockey jockey = new Jockey(null, "JockeyName 1", 23.1, null, null);
        Jockey insertedJockey = jockeyDao.insertJockey(jockey);
        Jockey newJockey = new Jockey(null, "newName", 50.0, null, null);
        newJockey.setId(insertedJockey.getId());
        newJockey.setCreated(insertedJockey.getCreated());
        newJockey.setDeleted(insertedJockey.getDeleted());

        newJockey.setUpdated(jockeyDao.updateJockey(newJockey.getId(), newJockey));

        Jockey updatedJockey = jockeyDao.findOneById(newJockey.getId());
        assertEquals(updatedJockey.getName(), newJockey.getName());
        assertEquals(updatedJockey.getSkill(), newJockey.getSkill());
        assertEquals(updatedJockey.getCreated(), newJockey.getCreated());
        assertTrue(updatedJockey.getUpdated().isAfter(insertedJockey.getUpdated()));
    }

    @Test(expected = NotFoundException.class)
    public void deleteJockeyTest() throws PersistenceException, NotFoundException {
        Jockey jockey = new Jockey(null, "JockeyName 1", 23.1, null, null);
        Jockey newJockey = jockeyDao.insertJockey(jockey);
        jockeyDao.deleteOneById(newJockey.getId());
        jockeyDao.findOneById(newJockey.getId());
    }
}
