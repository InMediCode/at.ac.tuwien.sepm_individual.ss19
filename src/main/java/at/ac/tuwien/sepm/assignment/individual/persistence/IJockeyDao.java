package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Jockey;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.exceptions.PersistenceException;

import java.util.ArrayList;

public interface IJockeyDao {

    /**
     * @param id of the jockey to find.
     * @return the jockey with the specified id.
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     * @throws NotFoundException    will be thrown if the jockey could not be found in the database.
     */
    Jockey findOneById(Integer id) throws PersistenceException, NotFoundException;

    /**
     * @return all jockeys as arraylist that are not marked as deleted
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    ArrayList<Jockey> getAll() throws PersistenceException;

    /**
     * @param name the jockey contains.
     * @param skill the jockey contains.
     * @return all jockeys as arraylist that are not marked as deleted
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    ArrayList<Jockey> getAllFilteredBy(String name, Double skill) throws PersistenceException;

    /**
     * @param jockey to insert into table
     * @return the jockey with the id
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    Jockey insertJockey(Jockey jockey) throws PersistenceException;

    /**
     * @param id of the jockey to delete.
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     * @throws NotFoundException    will be thrown if the jockey could not be found in the database.
     */
    void deleteOneById(Integer id) throws PersistenceException, NotFoundException;

}
