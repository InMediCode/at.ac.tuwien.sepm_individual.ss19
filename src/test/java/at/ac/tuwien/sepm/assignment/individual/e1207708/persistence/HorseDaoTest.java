package at.ac.tuwien.sepm.assignment.individual.e1207708.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
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
public class HorseDaoTest {

    @Autowired
    IHorseDao horseDao;
    @Autowired
    DBConnectionManager dbConnectionManager;

    /**
     * It is important to close the database connection after each test in order to clean the in-memory database
     */
    @After
    public void afterEachTest() throws PersistenceException {
        dbConnectionManager.closeConnection();
    }

    @Test
    public void insertHorseTest() throws PersistenceException {
        Horse horse = new Horse(null, "HorseName 1", "Breed 1", 40.0, 60.0, null, null);
        Horse newHorse = horseDao.insertHorse(horse);
        assertEquals(horse.getName(), newHorse.getName());
        assertEquals(horse.getMinSpeed(), newHorse.getMinSpeed());
        assertEquals(horse.getMaxSpeed(), newHorse.getMaxSpeed());
        assertEquals(horse.getBreed(), newHorse.getBreed());
        assertNotNull(newHorse.getId());
        assertNotNull(newHorse.getCreated());
        assertNotNull(newHorse.getUpdated());
    }

    @Test
    public void updateHorseNameTest() throws PersistenceException, NotFoundException {
        Horse horse = new Horse(null, "HorseName 1", "Breed 1", 40.0, 60.0, null, null);
        Horse newHorse = horseDao.insertHorse(horse);
        String newName = "newName";
        horseDao.updateHorseName(newHorse.getId(), newName);
        Horse updatedHorse = horseDao.findOneById(newHorse.getId());
        assertEquals(updatedHorse.getName(), newName);
        assertEquals(updatedHorse.getBreed(), newHorse.getBreed());
        assertEquals(updatedHorse.getMinSpeed(), newHorse.getMinSpeed());
        assertEquals(updatedHorse.getMaxSpeed(), newHorse.getMaxSpeed());
        assertEquals(updatedHorse.getCreated(), newHorse.getCreated());
        assertTrue(updatedHorse.getUpdated().isAfter(newHorse.getUpdated()));
    }

    @Test
    public void updateHorseBreedTest() throws PersistenceException, NotFoundException {
        Horse horse = new Horse(null, "HorseName 1", "Breed 1", 40.0, 60.0, null, null);
        Horse newHorse = horseDao.insertHorse(horse);
        String newBreed = "newBreed";
        horseDao.updateHorseBreed(newHorse.getId(), newBreed);
        Horse updatedHorse = horseDao.findOneById(newHorse.getId());
        assertEquals(updatedHorse.getName(), newHorse.getName());
        assertEquals(updatedHorse.getBreed(), newBreed);
        assertEquals(updatedHorse.getMinSpeed(), newHorse.getMinSpeed());
        assertEquals(updatedHorse.getMaxSpeed(), newHorse.getMaxSpeed());
        assertEquals(updatedHorse.getCreated(), newHorse.getCreated());
        assertTrue(updatedHorse.getUpdated().isAfter(newHorse.getUpdated()));
    }

    @Test
    public void updateHorseMinSpeedTest() throws PersistenceException, NotFoundException {
        Horse horse = new Horse(null, "HorseName 1", "Breed 1", 40.0, 60.0, null, null);
        Horse newHorse = horseDao.insertHorse(horse);
        Double newMinSpeed = 45.0;
        horseDao.updateHorseMinSpeed(newHorse.getId(), newMinSpeed);
        Horse updatedHorse = horseDao.findOneById(newHorse.getId());
        assertEquals(updatedHorse.getName(), newHorse.getName());
        assertEquals(updatedHorse.getBreed(), newHorse.getBreed());
        assertEquals(updatedHorse.getMinSpeed(), newMinSpeed);
        assertEquals(updatedHorse.getMaxSpeed(), newHorse.getMaxSpeed());
        assertEquals(updatedHorse.getCreated(), newHorse.getCreated());
        assertTrue(updatedHorse.getUpdated().isAfter(newHorse.getUpdated()));
    }

    @Test
    public void updateHorseMaxSpeedTest() throws PersistenceException, NotFoundException {
        Horse horse = new Horse(null, "HorseName 1", "Breed 1", 40.0, 60.0, null, null);
        Horse newHorse = horseDao.insertHorse(horse);
        Double newMaxSpeed = 45.0;
        horseDao.updateHorseMaxSpeed(newHorse.getId(), newMaxSpeed);
        Horse updatedHorse = horseDao.findOneById(newHorse.getId());
        assertEquals(updatedHorse.getName(), newHorse.getName());
        assertEquals(updatedHorse.getBreed(), newHorse.getBreed());
        assertEquals(updatedHorse.getMinSpeed(), newHorse.getMinSpeed());
        assertEquals(updatedHorse.getMaxSpeed(), newMaxSpeed);
        assertEquals(updatedHorse.getCreated(), newHorse.getCreated());
        assertTrue(updatedHorse.getUpdated().isAfter(newHorse.getUpdated()));
    }

    @Test(expected = NotFoundException.class)
    public void deleteHorseTest() throws PersistenceException, NotFoundException {
        Horse horse = new Horse(null, "HorseName 1", "Breed 1", 40.0, 60.0, null, null);
        Horse newHorse = horseDao.insertHorse(horse);
        horseDao.deleteOneById(newHorse.getId());
        horseDao.findOneById(newHorse.getId());
    }
}
