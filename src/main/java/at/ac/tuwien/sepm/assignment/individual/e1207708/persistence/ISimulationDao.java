package at.ac.tuwien.sepm.assignment.individual.e1207708.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.SimulationResult;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.e1207708.persistence.exceptions.PersistenceException;

import java.util.ArrayList;

public interface ISimulationDao {
    /**
     * @param id of the simulation to find.
     * @return the simulation with the specified id.
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     * @throws NotFoundException    will be thrown if the simulation could not be found in the database.
     */
    SimulationResult findOneById(Integer id) throws PersistenceException, NotFoundException;

    /**
     * @return all simulations as arraylist
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    ArrayList<SimulationResult> getAll() throws PersistenceException;

    /**
     * @param name the simulation contains.
     * @return all simulations as arraylist and contains the name
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    ArrayList<SimulationResult> getAllFilteredBy(String name) throws PersistenceException;

    /**
     * @param simulationResult to insert into table
     * @return the simulationResult with the id
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    SimulationResult insertSimulation(SimulationResult simulationResult) throws PersistenceException;
}
