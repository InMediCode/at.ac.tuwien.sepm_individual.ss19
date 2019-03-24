package at.ac.tuwien.sepm.assignment.individual.e1207708.service.impl;

import at.ac.tuwien.sepm.assignment.individual.e1207708.service.exceptions.BadRequestException;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.e1207708.persistence.IHorseDao;
import at.ac.tuwien.sepm.assignment.individual.e1207708.persistence.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.e1207708.service.IHorseService;
import at.ac.tuwien.sepm.assignment.individual.e1207708.service.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class HorseService implements IHorseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HorseService.class);
    private final IHorseDao horseDao;

    @Autowired
    public HorseService(IHorseDao horseDao) {
        this.horseDao = horseDao;
    }

    @Override
    public Horse findOneById(Integer id) throws ServiceException, NotFoundException {
        LOGGER.info("Get horse with id " + id);
        try {
            return horseDao.findOneById(id);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public ArrayList<Horse> getAll() throws ServiceException {
        LOGGER.info("Get all horses");
        try {
            return horseDao.getAll();
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public ArrayList<Horse> getAllFilteredBy(Horse horse) throws ServiceException, BadRequestException {
        LOGGER.info("Get all horses filtered");
        try {
            if (horse.getName() == null) {
                horse.setName("");
            }
            if (horse.getBreed() == null) {
                horse.setBreed("");
            }
            if (horse.getMinSpeed() == null) {
                horse.setMinSpeed(40.0);
            }
            if (horse.getMaxSpeed() == null) {
                horse.setMaxSpeed(60.0);
            }

            //validate only minSpeed and maxSpeed because name can be ""
            checkMinSpeedAndMaxSpeedValues(horse.getMinSpeed(), horse.getMaxSpeed());

            return horseDao.getAllFilteredBy(horse);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Horse insertHorse(Horse horse) throws  ServiceException, BadRequestException {
        LOGGER.info("Insert horse: " + horse);
        try {
            //validate horse variables
            checkHorse(horse);

            return horseDao.insertHorse(horse);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Horse updateHorse(int id, Horse horse) throws  ServiceException, NotFoundException, BadRequestException {
        LOGGER.info("Update horse " + id + " with " + horse);

        try {
            Horse oldHorse = findOneById(id);

            if (horse.getName() == null) {
                horse.setName(oldHorse.getName());
            }
            if (horse.getBreed() == null) {
                horse.setBreed(oldHorse.getBreed());
            }
            if (horse.getMinSpeed() == null) {
                horse.setMinSpeed(oldHorse.getMinSpeed());
            }
            if (horse.getMaxSpeed() == null) {
                horse.setMaxSpeed(oldHorse.getMaxSpeed());
            }

            checkHorse(horse);
            horse.setId(id);
            horse.setCreated(oldHorse.getCreated());
            horse.setUpdated(oldHorse.getUpdated());
            horse.setDeleted(oldHorse.getDeleted());

            horse.setUpdated(horseDao.updateHorse(id, horse));
            oldHorse.setMaxSpeed(horse.getMaxSpeed());

            return horse;

            /*//first check if all values can be validated before update
            Boolean updateName = checkName(horse.getName());
            Boolean updateBreed = horse.getBreed() != null;
            Boolean updateMinSpeed = false;
            Boolean updateMaxSpeed = false;

            if (horse.getMinSpeed() != null && horse.getMaxSpeed() != null) {
                //check if new minSpeed is smaller than new maxSpeed
                checkMinSpeedISSmallerThanMaxSpeed(horse.getMinSpeed(), horse.getMaxSpeed());
            }

            if (horse.getMinSpeed() != null) {
                checkMinSpeedValue(horse.getMinSpeed());
                //check if both will get updated or not => check with old or new maxSpeed
                Double checkMaxSpeed = horse.getMaxSpeed() == null ? oldHorse.getMaxSpeed() : horse.getMaxSpeed();

                if (horse.getMinSpeed() < checkMaxSpeed) {
                    //update MinSpeed
                    updateMinSpeed = true;
                } else {
                    throw new BadRequestException("new minSpeed " + horse.getMinSpeed() + " must be smaller than old maxSpeed" + oldHorse.getMaxSpeed());
                }
            }
            if (horse.getMaxSpeed() != null) {
                checkMaxSpeedValue(horse.getMaxSpeed());
                //check if both will get updated or not => check with old or new minSpeed
                Double checkMinSpeed = horse.getMinSpeed() == null ? oldHorse.getMinSpeed() : horse.getMinSpeed();

                if (checkMinSpeed > oldHorse.getMinSpeed()) {
                    //update MaxSpeed
                    updateMaxSpeed = true;
                } else {
                    throw new BadRequestException("new maxSpeed " + horse.getMaxSpeed() + " must be bigger than old minSpeed" + oldHorse.getMinSpeed());
                }
            }

            //update here to be sure no exceptions thrown and that not only a part is updated in DB
            if (updateName) {
                oldHorse.setUpdated(horseDao.updateHorseName(id, horse.getName()));
                oldHorse.setName(horse.getName());
            }
            if (updateBreed) {
                oldHorse.setUpdated(horseDao.updateHorseBreed(id, horse.getBreed()));
                oldHorse.setBreed(horse.getBreed());
            }
            if (updateMinSpeed) {
                oldHorse.setUpdated(horseDao.updateHorseMinSpeed(id, horse.getMinSpeed()));
                oldHorse.setMinSpeed(horse.getMinSpeed());
            }
            if (updateMaxSpeed) {
                oldHorse.setUpdated(horseDao.updateHorseMaxSpeed(id, horse.getMaxSpeed()));
                oldHorse.setMaxSpeed(horse.getMaxSpeed());
            }

            return oldHorse;*/
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteOneById(Integer id) throws  ServiceException, NotFoundException {
        LOGGER.info("Delete horse with id " + id);
        try {
            horseDao.deleteOneById(id);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private Boolean checkHorse(Horse horse) throws BadRequestException {
        LOGGER.info("Validate Horse");
        return checkName(horse.getName()) && checkMinSpeedAndMaxSpeedValues(horse.getMinSpeed(), horse.getMaxSpeed());
    }

    private Boolean checkName(String name) throws BadRequestException {
        if (name != null && name != "") {
            return true;
        } else {
            LOGGER.error("Problem while validating horse - name must be set");
            throw new BadRequestException("name must be set");
        }
    }

    private Boolean checkMinSpeedAndMaxSpeedValues(Double minSpeed, Double maxSpeed) throws BadRequestException {
        return checkMinSpeedValue(minSpeed) && checkMaxSpeedValue(maxSpeed) && checkMinSpeedISSmallerThanMaxSpeed(minSpeed, maxSpeed);
    }

    private Boolean checkMinSpeedValue(Double minSpeed) throws BadRequestException {
        if (minSpeed != null && minSpeed >= 40.0) {
            return true;
        } else {
            LOGGER.error("Problem while validating horse - " + minSpeed + " must be greather than or equal 40.0");
            throw new BadRequestException("minSpeed " + minSpeed + " must be greather than or equal 40.0");
        }
    }

    private Boolean checkMaxSpeedValue(Double maxSpeed) throws BadRequestException {
        if (maxSpeed != null && maxSpeed <= 60.0) {
            return true;
        } else {
            LOGGER.error("Problem while validating horse - " + maxSpeed + " must be smaller than or equal 60.0");
            throw new BadRequestException("maxSpeed " + maxSpeed + " must be smaller than or equal 60.0");
        }
    }

    private Boolean checkMinSpeedISSmallerThanMaxSpeed(Double minSpeed, Double maxSpeed) throws BadRequestException {
        if (minSpeed != null && maxSpeed != null && minSpeed < maxSpeed) {
            return true;
        } else {
            LOGGER.error("Problem while validating horse - "+ "minSpeed " + minSpeed + " must be smaller than maxSpeed " + maxSpeed);
            throw new BadRequestException("minSpeed " + minSpeed + " must be smaller than maxSpeed " + maxSpeed);
        }
    }
}
