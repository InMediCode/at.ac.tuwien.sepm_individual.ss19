package at.ac.tuwien.sepm.assignment.individual.e1207708.service.impl;

import at.ac.tuwien.sepm.assignment.individual.e1207708.service.ICalcService;
import at.ac.tuwien.sepm.assignment.individual.e1207708.service.exceptions.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CalcService implements ICalcService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HorseService.class);

    @Override
    public Double calcAvgSpeed(Double minSpeed, Double maxSpeed, Double skill, Double luckFactor) throws BadRequestException {
        LOGGER.info("Calculate Simulation AVG Speed");

        //validation only (needed) because it's a service and that it can be uses later somewhere else than SimulationService
        //minSpeed >= 40
        //maxSpeed <= 60
        //skill != null && Double value
        //luckFactor >= 0,95 && <= 1,05
        if (minSpeed < 40.0 || minSpeed == null) {
            LOGGER.error("Problem while validating simulation minSpeed - not >= 40");
            throw new BadRequestException("minSpeed not >= 40");
        } else if (maxSpeed > 60.0 || maxSpeed == null) {
            LOGGER.error("Problem while validating simulation maxSpeed - not <= 60");
            throw new BadRequestException("maxSpeed not <= 60");
        } else if (minSpeed > maxSpeed) {
            LOGGER.error("Problem while validating simulation minSpeed/maxSpeed - minSpeed greather than maxSpeed");
            throw new BadRequestException("minSpeed greather than maxSpeed");
        } else if (skill == null || Double.isInfinite(skill) || Double.isNaN(skill)) {
            LOGGER.error("Problem while validating simulation skill - not in given range");
            throw new BadRequestException("skill not in given range");
        } else if (luckFactor == null || luckFactor < 0.95 || luckFactor > 1.05) {
            LOGGER.error("Problem while validating simulation luckFactor - not in given range");
            throw new BadRequestException("luckFactor not in range");
        }

        return calcD(minSpeed, maxSpeed, skill, luckFactor);
    }

    @Override
    public Double calcHorseSpeed(Double minSpeed, Double maxSpeed, Double luckFactor) throws BadRequestException {
        LOGGER.info("Calculate Simulation Horse Speed");

        //validation only (needed) because it's a service and that it can be uses later somewhere else than SimulationService
        //minSpeed >= 40
        //maxSpeed <= 60
        //luckFactor >= 0,95 && <= 1,05
        if (minSpeed < 40.0 || minSpeed == null) {
            LOGGER.error("Problem while validating simulation minSpeed - not >= 40");
            throw new BadRequestException("minSpeed not >= 40");
        } else if (maxSpeed > 60.0 || maxSpeed == null) {
            LOGGER.error("Problem while validating simulation maxSpeed - not <= 60");
            throw new BadRequestException("maxSpeed not <= 60");
        } else if (minSpeed > maxSpeed) {
            LOGGER.error("Problem while validating simulation minSpeed/maxSpeed - minSpeed greather than maxSpeed");
            throw new BadRequestException("minSpeed greather than maxSpeed");
        } else if (luckFactor == null || luckFactor < 0.95 || luckFactor > 1.05) {
            LOGGER.error("Problem while validating simulation luckFactor - not in given range");
            throw new BadRequestException("luckFactor not in range");
        }

        return calcP(minSpeed, maxSpeed, luckFactor);
    }

    @Override
    public Double calcSkill(Double skill) throws BadRequestException {
        LOGGER.info("Calculate Simulation Skill");

        //validation only (needed) because it's a service and that it can be uses later somewhere else than SimulationService
        //skill != null && Double value
        if (skill == null || Double.isInfinite(skill) || Double.isNaN(skill)) {
            LOGGER.error("Problem while validating simulation skill - not in given range");
            throw new BadRequestException("skill not in given range");
        }

        return calcK(skill);
    }

    public Double calcP(Double minSpeed, Double maxSpeed, Double luckFactor) {
        //p=(g−0.95)∗(pmax−pmin)/(1.05−0.95)+pmin
        Double p = (luckFactor - 0.95) * (maxSpeed - minSpeed) / (1.05 - 0.95) + minSpeed;
        return getRoundedResult(p);
    }

    public Double calcK(Double skill) {
        //k‘=1+(0.15∗1/pi∗arctan(1/5∗k))
        Double k = 1.0 + (0.15 * 1.0 / Math.PI * Math.atan(1.0/5.0 * skill));

        return getRoundedResult(k);
    }

    public Double calcD(Double minSpeed, Double maxSpeed, Double skill, Double luckFactor) {
        //d=p∗k‘∗g
        return getRoundedResult(calcP(minSpeed, maxSpeed, luckFactor) * calcK(skill) * luckFactor);
    }

    public Double getRoundedResult(Double value) {
        int intValue = (int)(value * 100000);

        if (intValue % 10 >= 5) {
            intValue = (intValue / 10) + 1;
        } else {
            intValue = intValue / 10;
        }

        Double result = ((double)intValue) / 10000;
        return result;
    }
}
