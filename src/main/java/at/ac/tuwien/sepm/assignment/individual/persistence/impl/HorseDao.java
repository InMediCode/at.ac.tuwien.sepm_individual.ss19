package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.IHorseDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.util.DBConnectionManager;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;


@Repository
public class HorseDao implements IHorseDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(HorseDao.class);
    private final DBConnectionManager dbConnectionManager;

    @Autowired
    public HorseDao(DBConnectionManager dbConnectionManager) {
        this.dbConnectionManager = dbConnectionManager;
    }

    private static Horse dbResultToHorseDto(ResultSet result) throws SQLException {
        return new Horse(
            result.getInt("id"),
            result.getString("name"),
            result.getString("breed"),
            result.getDouble("min_speed"),
            result.getDouble("max_speed"),
            result.getTimestamp("created").toLocalDateTime(),
            result.getTimestamp("updated").toLocalDateTime());
    }


    @Override
    public Horse findOneById(Integer id) throws PersistenceException, NotFoundException {
        LOGGER.info("Get horse with id " + id);
        String sql = "SELECT * FROM Horse WHERE id=? AND deleted IS NOT TRUE";
        Horse horse = null;
        try {
            PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                horse = dbResultToHorseDto(result);
            }
        } catch (SQLException e) {
            LOGGER.error("Problem while executing SQL select statement for reading horse with id " + id, e);
            throw new PersistenceException("Could not read horses with id " + id, e);
        }
        if (horse != null) {
            return horse;
        } else {
            throw new NotFoundException("Could not find horse with id " + id);
        }
    }

    @Override
    public ArrayList<Horse> getAll() throws PersistenceException {
        LOGGER.info("Get all horses");
        String sql = "SELECT * FROM Horse WHERE deleted IS NOT TRUE";
        ArrayList<Horse> horses = new ArrayList<Horse>();
        try {
            PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                horses.add(dbResultToHorseDto(result));
            }
        } catch (SQLException e) {
            LOGGER.error("Problem while executing SQL select statement for reading all horses ", e);
            throw new PersistenceException("Could not read horses", e);
        }
        return horses;
    }

    @Override
    public ArrayList<Horse> getAllFilteredBy(String name, String breed, Double minSpeed, Double maxSpeed) throws PersistenceException {
        LOGGER.info("Get all horses filtered by name ", name, breed, minSpeed, maxSpeed);
        String sql = "SELECT * FROM Horse WHERE name LIKE ? AND breed LIKE ? AND min_speed>=? AND max_speed<=? AND deleted IS NOT TRUE";
        ArrayList<Horse> horses = new ArrayList<Horse>();
        try {
            PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql);
            statement.setString(1, "%"+name+"%");
            statement.setString(2, "%"+breed+"%");
            statement.setDouble(3, minSpeed);
            statement.setDouble(4, maxSpeed);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                horses.add(dbResultToHorseDto(result));
            }
        } catch (SQLException e) {
            LOGGER.error("Problem while executing SQL select statement for reading horses with ", e);
            throw new PersistenceException("Get all horses filtered by name ", e);
        }
        return horses;
    }

    @Override
    public Horse insertHorse(Horse horse) throws PersistenceException {
        LOGGER.info("Insert horse: " + horse);

        String sql = "INSERT INTO Horse (name, breed, min_speed, max_speed, created, updated, deleted, used_in_simulation, version) VALUES (?, ?, ?, ?, ?, ?, false, false, 1)";

        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            horse.setCreated(localDateTime);
            horse.setUpdated(localDateTime);

            PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, horse.getName());
            statement.setString(2, horse.getBreed());
            statement.setDouble(3, horse.getMinSpeed());
            statement.setDouble(4, horse.getMaxSpeed());
            statement.setTimestamp(5, new Timestamp(localDateTime.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli()));
            statement.setTimestamp(6, new Timestamp(localDateTime.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli()));
            statement.executeUpdate();

            ResultSet result = statement.getGeneratedKeys();
            result.next();

            horse.setId(result.getInt(1));
        } catch (SQLException e) {
            LOGGER.error("asdfasfd", e);
            throw new PersistenceException("asdfasfd", e);
        }

        return horse;
    }

    @Override
    public Horse updateHorse(Horse horse) throws PersistenceException, NotFoundException {
        LOGGER.info("Update horse: " + horse);

        String sql = "UPDATE Horse SET name=?, breed=?, min_speed WHERE id=? AND deleted IS NOT TRUE";

        /*try {
            //
        } catch (SQLException e) {
            LOGGER.error("asdfasfd", e);
            throw new PersistenceException("asdfasfd", e);
        }*/
        return null;
    }

    @Override
    public void deleteOneById(Integer id) throws PersistenceException, NotFoundException {
        LOGGER.info("Delete horse with id " + id);
        String sql = "UPDATE Horse SET deleted = TRUE WHERE id=? AND deleted IS NOT TRUE";
        int count = 0;
        try {
            PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql);
            statement.setInt(1, id);
            count = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Problem while executing SQL select statement for deleting horse with id " + id, e);
            throw new PersistenceException("Could not delete horses with id " + id, e);
        }
        if (count == 0) {
            throw new NotFoundException("Could not delete horse with id " + id);
        }
    }
}
