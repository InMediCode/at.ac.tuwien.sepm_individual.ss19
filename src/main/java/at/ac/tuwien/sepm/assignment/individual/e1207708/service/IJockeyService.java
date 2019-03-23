package at.ac.tuwien.sepm.assignment.individual.e1207708.service;

import at.ac.tuwien.sepm.assignment.individual.e1207708.service.exceptions.BadRequestException;
import at.ac.tuwien.sepm.assignment.individual.entity.Jockey;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.e1207708.service.exceptions.ServiceException;

import java.util.ArrayList;

public interface IJockeyService {

    /**
     * @param id of the jockey to find.
     * @return the jockey with the specified id.
     * @throws ServiceException  will be thrown if something goes wrong during data processing.
     * @throws NotFoundException will be thrown if the jockey could not be found in the system.
     */
    Jockey findOneById(Integer id) throws ServiceException, NotFoundException;

    /**
     * @return all jockeys as array that are not marked as deleted
     * @throws ServiceException  will be thrown if something goes wrong during data processing.
     */
    ArrayList<Jockey> getAll() throws ServiceException;

    /**
     * @param jockey with the name and skill to filter
     * @return all jockeys as arraylist that are not marked as deleted
     * @throws ServiceException  will be thrown if something goes wrong during data processing.
     * @throws BadRequestException will be thrown if params not valid.
     */
    ArrayList<Jockey> getAllFilteredBy(Jockey jockey) throws ServiceException, BadRequestException;

    /**
     *
     * @param jockey to insert into table
     * @return the jockey with the id
     * @throws ServiceException will be thrown if something goes wrong during data processing.
     * @throws BadRequestException will be thrown if params not valid.
     */
    Jockey insertJockey(Jockey jockey) throws  ServiceException, BadRequestException;

    /**
     * @param id of the jockey to update
     * @param jockey to update in table
     * @return the updated jockey
     * @throws ServiceException will be thrown if something goes wrong during data processing.
     * @throws NotFoundException will be thrown if the jockey could not be found in the system.
     * @throws BadRequestException will be thrown if params not valid.
     */
    Jockey updateJockey(int id, Jockey jockey) throws  ServiceException, NotFoundException, BadRequestException;

    /**
     * @param id of the jockey to delete.
     * @throws ServiceException  will be thrown if something goes wrong during data processing.
     * @throws NotFoundException will be thrown if the jockey could not be found in the system.
     */
    void deleteOneById(Integer id) throws  ServiceException, NotFoundException;
}
