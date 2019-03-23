package at.ac.tuwien.sepm.assignment.individual.e1207708.service;

import at.ac.tuwien.sepm.assignment.individual.entity.ParticipantResult;
import at.ac.tuwien.sepm.assignment.individual.e1207708.service.exceptions.ServiceException;

import java.util.ArrayList;

public interface IParticipantService {
    /**
     * @param simulationId of the participantResult
     * @return Arraylist of the participantResults with id
     * @throws ServiceException will be thrown if something goes wrong during data processing.
     */
    public ArrayList<ParticipantResult> getParticipantResultsBySimulationId(int simulationId) throws ServiceException;

    /**
     * @param simulationId id of the participantResult
     * @param participantResult to insert into table
     * @return the participantResult with id
     * @throws ServiceException will be thrown if something goes wrong during data processing.
     */
    ParticipantResult insertParticipant(int simulationId, ParticipantResult participantResult) throws ServiceException;
}
