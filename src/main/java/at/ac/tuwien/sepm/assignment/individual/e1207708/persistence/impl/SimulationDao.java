package at.ac.tuwien.sepm.assignment.individual.e1207708.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.SimulationResult;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.e1207708.persistence.ISimulationDao;
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
public class SimulationDao implements ISimulationDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimulationDao.class);
    private final DBConnectionManager dbConnectionManager;
    private final EpochMilliDateTimer epochMilliDateTimer;

    @Autowired
    public SimulationDao(DBConnectionManager dbConnectionManager, EpochMilliDateTimer epochMilliDateTimer) {
        this.dbConnectionManager = dbConnectionManager;
        this.epochMilliDateTimer = epochMilliDateTimer;
    }

    private static  SimulationResult dbResultToSimulationResultDto(ResultSet result) throws SQLException {
        return new SimulationResult(
            result.getInt("id"),
            result.getString("name"),
            result.getTimestamp("created").toLocalDateTime().minusHours(1),
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
    public ArrayList<SimulationResult> getAll() throws PersistenceException {
        LOGGER.info("Get all simulations");
        String sql = "SELECT * FROM Simulation";
        ArrayList<SimulationResult> simulationResults = new ArrayList<>();
        try {
            PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                simulationResults.add(dbResultToSimulationResultDto(result));
            }
        } catch (SQLException e) {
            LOGGER.error("Problem while executing SQL select statement for reading all simulations", e);
            throw new PersistenceException("Could not read all simulations ", e);
        }
        return simulationResults;
    }

    @Override
    public ArrayList<SimulationResult> getAllFilteredBy(String name) throws PersistenceException {
        LOGGER.info("Get all simulations filtered by name " + name);
        String sql = "SELECT * FROM Simulation WHERE name LIKE ?";
        ArrayList<SimulationResult> simulationResults = new ArrayList<>();
        try {
            PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql);
            statement.setString(1, "%"+name+"%");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                simulationResults.add(dbResultToSimulationResultDto(result));
            }
        } catch (SQLException e) {
            LOGGER.error("Problem while executing SQL select statement for reading all simulations filtered by name " + name, e);
            throw new PersistenceException("Could not read all simulations filtered by name " + name, e);
        }
        return simulationResults;
    }

    @Override
    public SimulationResult insertSimulation(SimulationResult simulationResult) throws PersistenceException {
        LOGGER.info("Insert Simulation: " + simulationResult);

        String sql = "INSERT INTO Simulation (name, created) VALUES (?, ?)";

        try {
            //replaced nano because saved in DB only with EpochMilli
            LocalDateTime localDateTime = epochMilliDateTimer.getLocalDateTime();

            simulationResult.setCreated(localDateTime);

            PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, simulationResult.getName());
            statement.setTimestamp(2, new Timestamp(localDateTime.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli()));
            statement.executeUpdate();

            ResultSet result = statement.getGeneratedKeys();
            result.next();

            simulationResult.setId(result.getInt(1));
        } catch (SQLException e) {
            LOGGER.error("Problem while executing SQL select statement for inserting simulation ", e);
            throw new PersistenceException("Could not insert simulation ", e);
        }

        return simulationResult;
    }
}
