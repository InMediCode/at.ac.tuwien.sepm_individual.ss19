package at.ac.tuwien.sepm.assignment.individual.e1207708.service;

import at.ac.tuwien.sepm.assignment.individual.e1207708.service.exceptions.BadRequestException;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.e1207708.service.exceptions.ServiceException;

import java.util.ArrayList;

public interface IHorseService {

    /**
     * @param id of the horse to find.
     * @return the horse with the specified id.
     * @throws ServiceException  will be thrown if something goes wrong during data processing.
     * @throws NotFoundException will be thrown if the horse could not be found in the system.
     */
    Horse findOneById(Integer id) throws ServiceException, NotFoundException;

    /**
     * @return all horses as array that are not marked as deleted
     * @throws ServiceException  will be thrown if something goes wrong during data processing.
     */
    ArrayList<Horse> getAll() throws ServiceException;

    /**
     * @param name the horse contains.
     * @param breed the horse contains.
     * @param minSpeed greater or equal of the horse.
     * @param maxSpeed smaller or equal of the horse.
     * @return all horses as arraylist that are not marked as deleted
     * @throws ServiceException  will be thrown if something goes wrong during data processing.
     */
    ArrayList<Horse> getAllFilteredBy(String name, String breed, Double minSpeed, Double maxSpeed) throws ServiceException;

    /**
     *
     * @param horse to insert into table
     * @return the horse with the id
     * @throws ServiceException will be thrown if something goes wrong during data processing.
     * @throws BadRequestException will be thrown if params not valid.
     */
    Horse insertHorse(Horse horse) throws  ServiceException, BadRequestException;

    /**
     * @param id of the horse to update
     * @param horse to update in table
     * @return the updated horse
     * @throws ServiceException will be thrown if something goes wrong during data processing.
     * @throws NotFoundException will be thrown if the horse could not be found in the system.
     * @throws BadRequestException will be thrown if params not valid.
     */
    Horse updateHorse(int id, Horse horse) throws  ServiceException, NotFoundException, BadRequestException;

    /**
     * @param id of the horse to delete.
     * @throws ServiceException  will be thrown if something goes wrong during data processing.
     * @throws NotFoundException will be thrown if the horse could not be found in the system.
     */
    void deleteOneById(Integer id) throws  ServiceException, NotFoundException;
}
