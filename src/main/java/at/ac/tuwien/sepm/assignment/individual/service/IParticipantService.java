package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.entity.ParticipantResult;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;

public interface IParticipantService {
    /**
     * @param simulationId id oft the simulation
     * @param participantResult to insert into table
     * @return the participantResult with id
     * @throws ServiceException will be thrown if something goes wrong during data processing.
     */
    ParticipantResult insertParticipant(int simulationId, ParticipantResult participantResult) throws ServiceException;
}
