package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.entity.Simulation;
import at.ac.tuwien.sepm.assignment.individual.entity.SimulationResult;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;

public interface ISimulationService {
    /**
     *
     * @param simulation to insert into table
     * @return the calculated simulationResult
     * @throws ServiceException will be thrown if something goes wrong during data processing.
     */
    SimulationResult insertSimulation(Simulation simulation) throws ServiceException;
}
