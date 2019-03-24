package at.ac.tuwien.sepm.assignment.individual.e1207708.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.e1207708.persistence.exceptions.PersistenceException;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface IHorseDao {

    /**
     * @param id of the horse to find.
     * @return the horse with the specified id.
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     * @throws NotFoundException    will be thrown if the horse could not be found in the database.
     */
    Horse findOneById(Integer id) throws PersistenceException, NotFoundException;

    /**
     * @return all horses as arraylist that are not marked as deleted
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    ArrayList<Horse> getAll() throws PersistenceException;

    /**
     * @param horse with the name, breed, minSpeed and maxSpeed to filter
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    ArrayList<Horse> getAllFilteredBy(Horse horse) throws PersistenceException;

    /**
     * @param horse to insert into table
     * @return the horse with the id
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    Horse insertHorse(Horse horse) throws PersistenceException;

    /**
     * @param id of the horse to update
     * @param horse to update in table
     * @return the updated DateTime
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     * @throws NotFoundException    will be thrown if the horse could not be found in the database.
     */
    LocalDateTime updateHorse(int id, Horse horse) throws PersistenceException, NotFoundException;

    /**
     * @param id of the horse to delete.
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     * @throws NotFoundException    will be thrown if the horse could not be found in the database.
     */
    void deleteOneById(Integer id) throws PersistenceException, NotFoundException;

}
