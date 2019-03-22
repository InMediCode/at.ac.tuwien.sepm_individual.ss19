package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.ParticipantResult;
import at.ac.tuwien.sepm.assignment.individual.persistence.IParticipantDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.util.DBConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@Repository
public class ParticipantDao implements IParticipantDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimulationDao.class);
    private final DBConnectionManager dbConnectionManager;

    @Autowired
    public ParticipantDao(DBConnectionManager dbConnectionManager) {
        this.dbConnectionManager = dbConnectionManager;
    }

    private static  ParticipantResult dbResultToParticipantResultDto(ResultSet result) throws SQLException {
        return new ParticipantResult(
            result.getInt("id"),
            result.getInt("rank"),
            result.getString("horse_name"),
            result.getString("jockey_name"),
            result.getDouble("avg_speed"),
            result.getDouble("horse_speed"),
            result.getDouble("skill"),
            result.getDouble("luck_factor"));
    }

    @Override
    public ArrayList<ParticipantResult> getParticipantResultsBySimulationId(int simulationId) throws PersistenceException {
        LOGGER.info("Get all participantResults for simulatioId " + simulationId);

        String sql = "SELECT * FROM Participants WHERE simulation_id=? ORDER BY rank ASC";

        ArrayList<ParticipantResult> participantResults = new ArrayList<>();

        try {
            PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql);
            statement.setInt(1, simulationId);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                participantResults.add(dbResultToParticipantResultDto(result));
            }
        } catch (SQLException e) {
            LOGGER.error("Problem while executing SQL select statement for reading participantResults ", e);
            throw new PersistenceException("Could not read participantResults", e);
        }

        return participantResults;
    }

    @Override
    public ParticipantResult insertParticipant(int simulationId, ParticipantResult participantResult) throws PersistenceException {
        LOGGER.info("Insert into participants: " + participantResult);

        String sql = "INSERT INTO Participants (simulation_id, rank, horse_name, jockey_name, avg_speed, horse_speed, skill, luck_factor) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, simulationId);
            statement.setInt(2, participantResult.getRank());
            statement.setString(3, participantResult.getHorseName());
            statement.setString(4, participantResult.getJockeyName());
            statement.setDouble(5, participantResult.getAvgSpeed());
            statement.setDouble(6, participantResult.getHorseSpeed());
            statement.setDouble(7, participantResult.getSkill());
            statement.setDouble(8, participantResult.getLuckFactor());
            statement.executeUpdate();

            ResultSet result = statement.getGeneratedKeys();
            result.next();

            participantResult.setId(result.getInt(1));
        } catch (SQLException e) {
            LOGGER.error("ToDo: Simulation can not be inserted", e);
            throw new PersistenceException("asdfsadf", e);
        }

        return participantResult;
    }
}
