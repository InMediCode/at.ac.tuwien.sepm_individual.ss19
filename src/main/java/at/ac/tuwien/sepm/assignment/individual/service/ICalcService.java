package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;

public interface ICalcService {
    /**
     * @param minSpeed of the horse
     * @param  maxSpeed of the horse
     * @param  skill of the jockey
     * @param luckFactor of the participant
     * @return the calculated avg speed
     * @throws ServiceException will be thrown if something goes wrong during data processing.
     */
    Double calcAvgSpeed(Double minSpeed, Double maxSpeed, Double skill, Double luckFactor) throws ServiceException;

    /**
     * @param minSpeed of the horse
     * @param  maxSpeed of the horse
     * @param luckFactor of the participant
     * @return the calculated horse speed
     * @throws ServiceException will be thrown if something goes wrong during data processing.
     */
    Double calcHorseSpeed(Double minSpeed, Double maxSpeed, Double luckFactor) throws ServiceException;
}
