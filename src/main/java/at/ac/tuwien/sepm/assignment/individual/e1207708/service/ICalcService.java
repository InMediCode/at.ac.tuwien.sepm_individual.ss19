package at.ac.tuwien.sepm.assignment.individual.e1207708.service;

import at.ac.tuwien.sepm.assignment.individual.e1207708.service.exceptions.BadRequestException;
import at.ac.tuwien.sepm.assignment.individual.e1207708.service.exceptions.ServiceException;

public interface ICalcService {
    /**
     * @param minSpeed of the horse
     * @param  maxSpeed of the horse
     * @param  skill of the jockey
     * @param luckFactor of the participant
     * @return the calculated avg speed
     * @throws BadRequestException will be thrown if params not valid.
     */
    Double calcAvgSpeed(Double minSpeed, Double maxSpeed, Double skill, Double luckFactor) throws BadRequestException;

    /**
     * @param minSpeed of the horse
     * @param  maxSpeed of the horse
     * @param luckFactor of the participant
     * @return the calculated horse speed
     * @throws BadRequestException will be thrown if params not valid.
     */
    Double calcHorseSpeed(Double minSpeed, Double maxSpeed, Double luckFactor) throws BadRequestException;

    /**
     * @param skill of the jockey
     * @return the calculated skill
     * @throws BadRequestException will be thrown if params not valid.
     */
    Double calcSkill(Double skill) throws BadRequestException;
}
