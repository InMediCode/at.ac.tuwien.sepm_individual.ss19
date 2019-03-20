package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Simulation;
import at.ac.tuwien.sepm.assignment.individual.persistence.ISimulationDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.service.ISimulationService;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimulationService implements ISimulationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HorseService.class);
    private final ISimulationDao simulationDao;

    @Autowired
    public SimulationService(ISimulationDao simulationDao) {
        this.simulationDao = simulationDao;
    }

    @Override
    public Simulation insertSimulation(Simulation simulation) throws ServiceException {
        LOGGER.info("Inser Simulation: " + simulation);

        try {
            return simulationDao.insertSimulation(simulation);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
