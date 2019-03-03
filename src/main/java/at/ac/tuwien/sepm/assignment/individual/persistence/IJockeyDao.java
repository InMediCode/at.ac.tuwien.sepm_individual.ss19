package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Jockey;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.exceptions.PersistenceException;

public interface IJockeyDao {

    /**
     * @param id of the jockey to find.
     * @return the jockey with the specified id.
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     * @throws NotFoundException    will be thrown if the jockey could not be found in the database.
     */
    Jockey findOneById(Integer id) throws PersistenceException, NotFoundException;

}
