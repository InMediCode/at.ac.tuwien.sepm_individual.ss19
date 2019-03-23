package at.ac.tuwien.sepm.assignment.individual.e1207708.service;

import at.ac.tuwien.sepm.assignment.individual.e1207708.service.exceptions.ServiceException;

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

    /**
     * @param skill of the jockey
     * @return the calculated skill
     * @throws ServiceException will be thrown if something goes wrong during data processing.
     */
    Double calcSkill(Double skill) throws ServiceException;
}