package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Simulation;
import at.ac.tuwien.sepm.assignment.individual.persistence.ISimulationDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.util.DBConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class SimulationDao implements ISimulationDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimulationDao.class);
    private final DBConnectionManager dbConnectionManager;

    @Autowired
    public SimulationDao(DBConnectionManager dbConnectionManager) {
        this.dbConnectionManager = dbConnectionManager;
    }

    @Override
    public Simulation insertSimulation(Simulation simulation) throws PersistenceException {
        LOGGER.info("Insert Simulation: " + simulation);

        String sql = "";

        /*try {
            //
        } catch (SQLException e) {
            LOGGER.error("asdfasfd", e);
            throw new PersistenceException("asdfasfd", e);
        }*/

        return simulation;
    }

    /*LOGGER.info("Insert horse: " + horse);

    String sql = "INSERT INTO Horse (name, breed, min_speed, max_speed, created, updated, deleted) VALUES (?, ?, ?, ?, ?, ?, false)";

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

        return horse;*/
}
