package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Simulation;
import at.ac.tuwien.sepm.assignment.individual.persistence.exceptions.PersistenceException;

public interface ISimulationDao {
    /**
     * @param simulation to insert into table
     * @return the simulation with the id
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    Simulation insertSimulation(Simulation simulation) throws PersistenceException;
}
