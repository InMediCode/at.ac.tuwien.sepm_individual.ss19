package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Jockey;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.IJockeyDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.util.DBConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        String sql = "SELECT * FROM Jockey WHERE id=?";
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
}
