package at.ac.tuwien.sepm.assignment.individual.e1207708.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Jockey;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.e1207708.persistence.IJockeyDao;
import at.ac.tuwien.sepm.assignment.individual.e1207708.persistence.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.e1207708.persistence.util.DBConnectionManager;
import at.ac.tuwien.sepm.assignment.individual.util.localdatetime.EpochMilliDateTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

@Repository
public class JockeyDao implements IJockeyDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(JockeyDao.class);
    private final DBConnectionManager dbConnectionManager;
    private final EpochMilliDateTimer epochMilliDateTimer;

    @Autowired
    public JockeyDao(DBConnectionManager dbConnectionManager, EpochMilliDateTimer epochMilliDateTimer) {
        this.dbConnectionManager = dbConnectionManager;
        this.epochMilliDateTimer = epochMilliDateTimer;
    }

    private static  Jockey dbResultToJockeyDto(ResultSet result) throws SQLException {
        return new Jockey(
                result.getInt("id"),
                result.getString("name"),
                result.getDouble("skill"),
                result.getTimestamp("created").toLocalDateTime().minusHours(1),
                result.getTimestamp("updated").toLocalDateTime().minusHours(1));
    }

    @Override
    public Jockey findOneById(Integer id) throws PersistenceException, NotFoundException {
        LOGGER.info("Get jockey with id " + id);
        String sql = "SELECT * FROM Jockey WHERE id=? AND deleted IS NOT TRUE";
        Jockey jockey = null;
        try {
            PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                jockey = dbResultToJockeyDto(result);
            }
        } catch (SQLException e) {
            LOGGER.error("Problem while executing SQL select statement for reading jockey with id " + id, e);
            throw new PersistenceException("Could not read jockey with id " + id, e);
        }
        if (jockey != null) {
            return jockey;
        } else {
            LOGGER.error("Could not find jockey with id " + id);
            throw new NotFoundException("Could not find jockey with id " + id);
        }
    }

    @Override
    public ArrayList<Jockey> getAll() throws PersistenceException {
        LOGGER.info("Get all jockeys");
        String sql = "SELECT * FROM Jockey WHERE deleted IS NOT TRUE";
        ArrayList<Jockey> jockey = new ArrayList<Jockey>();
        try {
            PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                jockey.add(dbResultToJockeyDto(result));
            }
        } catch (SQLException e) {
            LOGGER.error("Problem while executing SQL select statement for reading all jockeys ", e);
            throw new PersistenceException("Could not read jockeys", e);
        }
        return jockey;
    }

    @Override
    public ArrayList<Jockey> getAllFilteredBy(Jockey jockey) throws PersistenceException {
        LOGGER.info("Get all jockeys filtered by " + jockey);
        String sql = "SELECT * FROM Jockey WHERE name LIKE ? AND  skill>=? AND deleted IS NOT TRUE";
        ArrayList<Jockey> jockeys = new ArrayList<Jockey>();
        try {
            PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql);
            statement.setString(1, "%"+jockey.getName()+"%");
            statement.setDouble(2, jockey.getSkill());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                jockeys.add(dbResultToJockeyDto(result));
            }
        } catch (SQLException e) {
            LOGGER.error("Problem while executing SQL select statement for reading jockeys with ", e);
            throw new PersistenceException("Get all jockeys filtered by name ", e);
        }
        return jockeys;
    }

    @Override
    public Jockey insertJockey(Jockey jockey) throws PersistenceException {
        LOGGER.info("Insert jockey: " + jockey);

        String sql = "INSERT INTO Jockey (name, skill, created, updated, deleted) VALUES (?, ?, ?, ?, false)";

        try {
            //replaced nano because saved in DB only with EpochMilli
            LocalDateTime localDateTime = epochMilliDateTimer.getLocalDateTime();

            jockey.setCreated(localDateTime);
            jockey.setUpdated(localDateTime);

            PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, jockey.getName());
            statement.setDouble(2, jockey.getSkill());
            statement.setTimestamp(3, new Timestamp(localDateTime.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli()));
            statement.setTimestamp(4, new Timestamp(localDateTime.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli()));
            statement.executeUpdate();

            ResultSet result = statement.getGeneratedKeys();
            result.next();

            jockey.setId(result.getInt(1));
        } catch (SQLException e) {
            LOGGER.error("Problem while executing SQL select statement for inserting jockey ", e);
            throw new PersistenceException("Could not insert jockey", e);
        }

        return jockey;
    }

    @Override
    public LocalDateTime updateJockey(int id, Jockey jockey) throws PersistenceException, NotFoundException {
        LOGGER.info("Update jockey " + jockey);

        String sql = "UPDATE Jockey SET name=?, skill=?, updated=? WHERE id=? AND deleted IS NOT TRUE";
        int count = 0;
        try {
            //replaced nano because saved in DB only with EpochMilli
            LocalDateTime updated = epochMilliDateTimer.getLocalDateTime();

            PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql);
            statement.setString(1, jockey.getName());
            statement.setDouble(2, jockey.getSkill());
            statement.setTimestamp(3, new Timestamp(updated.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli()));
            statement.setInt(4, id);
            count = statement.executeUpdate();

            if (count == 0) {
                LOGGER.error("Could not update jockey");
                throw new NotFoundException("Could not update jockey");
            } else {
                return updated;
            }
        } catch (SQLException e) {
            LOGGER.error("Problem while executing SQL select statement for updating jockey", e);
            throw new PersistenceException("Could not update jockey", e);
        }
    }

    @Override
    public void deleteOneById(Integer id) throws PersistenceException, NotFoundException {
        LOGGER.info("Delete jockey with id " + id);
        String sql = "UPDATE Jockey SET deleted = TRUE WHERE id=? AND deleted IS NOT TRUE";
        int count = 0;
        try {
            PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql);
            statement.setInt(1, id);
            count = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Problem while executing SQL select statement for deleting jockey with id " + id, e);
            throw new PersistenceException("Could not delete jockeys with id " + id, e);
        }
        if (count == 0) {
            LOGGER.error("Could not find jockey with id " + id);
            throw new NotFoundException("Could not find jockey with id " + id);
        }
    }
}
