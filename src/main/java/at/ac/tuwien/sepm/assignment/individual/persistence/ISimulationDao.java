package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Simulation;
import at.ac.tuwien.sepm.assignment.individual.entity.SimulationResult;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.exceptions.PersistenceException;

public interface ISimulationDao {
    /**
     * @param id of the simulation to find.
     * @return the simulation with the specified id.
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     * @throws NotFoundException    will be thrown if the simulation could not be found in the database.
     */
    SimulationResult findOneById(Integer id) throws PersistenceException, NotFoundException;

    /**
     * @param simulationResult to insert into table
     * @return the simulationResult with the id
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    SimulationResult insertSimulation(SimulationResult simulationResult) throws PersistenceException;
}
