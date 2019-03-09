package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.entity.Jockey;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;

public interface IJockeyService {

    /**
     * @param id of the jockey to find.
     * @return the jockey with the specified id.
     * @throws ServiceException  will be thrown if something goes wrong during data processing.
     * @throws NotFoundException will be thrown if the jockey could not be found in the system.
     */
    Jockey findOneById(Integer id) throws ServiceException, NotFoundException;

    /**
     *
     * @param jockey to insert into table
     * @return the jockey with the id
     * @throws ServiceException will be thrown if something goes wrong during data processing.
     */
    Jockey insertJockey(Jockey jockey) throws  ServiceException;
}
