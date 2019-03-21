package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Simulation;
import at.ac.tuwien.sepm.assignment.individual.entity.SimulationResult;
import at.ac.tuwien.sepm.assignment.individual.persistence.exceptions.PersistenceException;

public interface ISimulationDao {
    /**
     * @param simulationResult to insert into table
     * @return the simulationResult with the id
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    SimulationResult insertSimulation(SimulationResult simulationResult) throws PersistenceException;
}
