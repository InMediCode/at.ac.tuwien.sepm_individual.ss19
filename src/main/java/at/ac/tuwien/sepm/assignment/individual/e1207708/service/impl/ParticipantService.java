package at.ac.tuwien.sepm.assignment.individual.e1207708.service.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.ParticipantResult;
import at.ac.tuwien.sepm.assignment.individual.e1207708.persistence.IParticipantDao;
import at.ac.tuwien.sepm.assignment.individual.e1207708.persistence.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.e1207708.service.IParticipantService;
import at.ac.tuwien.sepm.assignment.individual.e1207708.service.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ParticipantService implements IParticipantService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantService.class);
    private final IParticipantDao participantDao;

    @Autowired
    public ParticipantService(IParticipantDao participantDao) {
        this.participantDao = participantDao;
    }

    @Override
    public ArrayList<ParticipantResult> getParticipantResultsBySimulationId(int simulationId) throws ServiceException {
        LOGGER.info("Get ParticipantResult with simulationId " + simulationId);
        try {
            return participantDao.getParticipantResultsBySimulationId(simulationId);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public ParticipantResult insertParticipant(int simulationId, ParticipantResult participantResult) throws ServiceException {
        LOGGER.info("Insert ParticipantResult with simulationId " + simulationId);
        try {
            return participantDao.insertParticipant(simulationId, participantResult);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
