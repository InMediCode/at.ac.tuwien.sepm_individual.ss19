package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;

public interface ICalcService {
    /**
     * @param minSpeed of the horse
     * @param  maxSpeed of the horse
     * @param  skill of the jockey
     * @param luckFactor of the participant
     * @return the calculated avg speed
     */
    Double calcAvgSpeed(Double minSpeed, Double maxSpeed, Double skill, Double luckFactor) throws ServiceException;
}
