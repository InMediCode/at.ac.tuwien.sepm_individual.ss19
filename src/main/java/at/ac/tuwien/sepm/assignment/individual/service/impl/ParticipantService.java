package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.ParticipantResult;
import at.ac.tuwien.sepm.assignment.individual.persistence.IParticipantDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.service.IParticipantService;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;
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
        try {
            return participantDao.getParticipantResultsBySimulationId(simulationId);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public ParticipantResult insertParticipant(int simulationId, ParticipantResult participantResult) throws ServiceException {
        try {
            return participantDao.insertParticipant(simulationId, participantResult);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
