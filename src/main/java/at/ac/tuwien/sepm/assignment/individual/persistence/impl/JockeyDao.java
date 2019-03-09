package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Jockey;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.IJockeyDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.util.DBConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Repository
public class JockeyDao implements IJockeyDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(JockeyDao.class);
    private final DBConnectionManager dbConnectionManager;

    @Autowired
    public JockeyDao(DBConnectionManager dbConnectionManager) { this.dbConnectionManager = dbConnectionManager; }

    private static  Jockey dbResultToJockeyDto(ResultSet result) throws SQLException {
        return new Jockey(
                result.getInt("id"),
                result.getString("name"),
                result.getDouble("skill"),
                result.getTimestamp("created").toLocalDateTime(),
                result.getTimestamp("updated").toLocalDateTime());
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
            throw new NotFoundException("Could not find jockey with id " + id);
        }
    }

    @Override
    public Jockey insertJockey(Jockey jockey) throws PersistenceException {
        LOGGER.info("Insert jockey: " + jockey);

        String sql = "INSERT INTO Jockey (name, skill, created, updated, deleted) VALUES (?, ?, ?, ?, false)";

        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            jockey.setCreated(localDateTime);
            jockey.setUpdated(localDateTime);

            PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, jockey.getName());
            statement.setDouble(2, jockey.getSkill());
            statement.setTimestamp(3, new Timestamp(localDateTime.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli()));
            statement.setTimestamp(4, new Timestamp(localDateTime.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli()));

            ResultSet result = statement.getGeneratedKeys();
            result.next();

            jockey.setId(result.getInt(1));
        } catch (SQLException e) {
            LOGGER.error("asdfasfd", e);
            throw new PersistenceException("asdfasfd", e);
        }

        return jockey;
    }
}
