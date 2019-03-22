package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.ParticipantResult;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.exceptions.PersistenceException;

import java.util.ArrayList;

public interface IParticipantDao {
    /**
     * @param simulationId of the participantResult to find.
     * @return the simulation with the specified id.
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    ArrayList<ParticipantResult> getParticipantResultsBySimulationId(int simulationId) throws PersistenceException;

    /**
     * @param simulationId of the participantResult
     * @param participantResult to insert into table
     * @return the participantResult with the id
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    ParticipantResult insertParticipant(int simulationId, ParticipantResult participantResult) throws PersistenceException;
}
