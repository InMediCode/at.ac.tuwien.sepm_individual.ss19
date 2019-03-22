package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Simulation;
import at.ac.tuwien.sepm.assignment.individual.entity.SimulationResult;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.ISimulationDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.util.DBConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

@Repository
public class SimulationDao implements ISimulationDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimulationDao.class);
    private final DBConnectionManager dbConnectionManager;

    @Autowired
    public SimulationDao(DBConnectionManager dbConnectionManager) {
        this.dbConnectionManager = dbConnectionManager;
    }

    private static  SimulationResult dbResultToSimulationResultDto(ResultSet result) throws SQLException {
        return new SimulationResult(
            result.getInt("id"),
            result.getString("name"),
            result.getTimestamp("created").toLocalDateTime(),
            new ArrayList<>());
    }

    @Override
    public SimulationResult findOneById(Integer id) throws PersistenceException, NotFoundException {
        LOGGER.info("Get simulation with id " + id);
        String sql = "SELECT * FROM Simulation WHERE id=?";
        SimulationResult simulationResult = null;
        try {
            PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                simulationResult = dbResultToSimulationResultDto(result);
            }
        } catch (SQLException e) {
            LOGGER.error("Problem while executing SQL select statement for reading simulation with id " + id, e);
            throw new PersistenceException("Could not read simulation with id " + id, e);
        }
        if (simulationResult != null) {
            return simulationResult;
        } else {
            throw new NotFoundException("Could not find simulation with id " + id);
        }
    }

    @Override
    public SimulationResult insertSimulation(SimulationResult simulationResult) throws PersistenceException {
        LOGGER.info("Insert Simulation: " + simulationResult);

        String sql = "INSERT INTO Simulation (name, created) VALUES (?, ?)";

        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            simulationResult.setCreated(localDateTime);

            PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, simulationResult.getName());
            statement.setTimestamp(2, new Timestamp(localDateTime.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli()));
            statement.executeUpdate();

            ResultSet result = statement.getGeneratedKeys();
            result.next();

            simulationResult.setId(result.getInt(1));
        } catch (SQLException e) {
            LOGGER.error("asdfasfd", e);
            throw new PersistenceException("asdfasfd", e);
        }

        return simulationResult;
    }
}
