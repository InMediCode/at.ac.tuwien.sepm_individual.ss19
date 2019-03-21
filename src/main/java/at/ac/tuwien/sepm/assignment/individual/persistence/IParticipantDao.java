package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.ParticipantResult;
import at.ac.tuwien.sepm.assignment.individual.persistence.exceptions.PersistenceException;

public interface IParticipantDao {
    /**
     * @param participantResult to insert into table
     * @return the participantResult with the id
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    ParticipantResult insertParticipant(int simulationId, ParticipantResult participantResult) throws PersistenceException;
}
