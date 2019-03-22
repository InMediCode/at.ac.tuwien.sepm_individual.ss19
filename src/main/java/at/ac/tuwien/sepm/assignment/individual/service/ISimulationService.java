package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.entity.Simulation;
import at.ac.tuwien.sepm.assignment.individual.entity.SimulationResult;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;

public interface ISimulationService {
    /**
     * @param id of the simulation to find.
     * @return the simulationResult with the specified id.
     * @throws ServiceException  will be thrown if something goes wrong during data processing.
     * @throws NotFoundException will be thrown if the simulation could not be found in the system.
     */
    SimulationResult findOneById(Integer id) throws ServiceException, NotFoundException;

    /**
     *
     * @param simulation to insert into table
     * @return the calculated simulationResult
     * @throws ServiceException will be thrown if something goes wrong during data processing.
     */
    SimulationResult insertSimulation(Simulation simulation) throws ServiceException;
}
