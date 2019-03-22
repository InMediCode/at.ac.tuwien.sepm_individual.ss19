package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.IHorseDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.service.IHorseService;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;
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
    public ArrayList<Horse> getAllFilteredBy(String name, String breed, Double minSpeed, Double maxSpeed) throws ServiceException {
        LOGGER.info("Get all horses with name " + name);
        try {
            return horseDao.getAllFilteredBy(name, breed, minSpeed, maxSpeed);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Horse insertHorse(Horse horse) throws  ServiceException {
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
    public Horse updateHorse(int id, Horse horse) throws  ServiceException, NotFoundException {
        LOGGER.info("Update horse " + horse + "with id " + id);

        try {
            Horse oldHorse = findOneById(id);

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
                if (horse.getMinSpeed() < oldHorse.getMaxSpeed()) {
                    //update MinSpeed
                    updateMinSpeed = true;
                } else {
                    throw new ServiceException("new minSpeed " + horse.getMinSpeed() + " must be smaller than old maxSpeed" + oldHorse.getMaxSpeed());
                }
            }
            if (horse.getMaxSpeed() != null) {
                checkMaxSpeedValue(horse.getMaxSpeed());
                if (horse.getMaxSpeed() > oldHorse.getMinSpeed()) {
                    //update MaxSpeed
                    updateMaxSpeed = true;
                } else {
                    throw new ServiceException("new maxSpeed " + horse.getMaxSpeed() + " must be bigger than old minSpeed" + oldHorse.getMinSpeed());
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

            return oldHorse;
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

    private Boolean checkHorse(Horse horse) throws ServiceException {
        return checkName(horse.getName()) && checkMinSpeedAndMaxSpeedValues(horse.getMinSpeed(), horse.getMaxSpeed());
    }

    private Boolean checkName(String name) throws ServiceException {
        if (name != null && name != "") {
            return true;
        } else {
            throw new ServiceException("name must be set");
        }
    }

    private Boolean checkMinSpeedAndMaxSpeedValues(Double minSpeed, Double maxSpeed) throws ServiceException {
        return checkMinSpeedValue(minSpeed) && checkMaxSpeedValue(maxSpeed) && checkMinSpeedISSmallerThanMaxSpeed(minSpeed, maxSpeed);
    }

    private Boolean checkMinSpeedValue(Double minSpeed) throws ServiceException {
        if (minSpeed != null && minSpeed >= 40.0) {
            return true;
        } else {
            throw new ServiceException("minSpeed " + minSpeed + " must be greather than or equal 40.0");
        }
    }

    private Boolean checkMaxSpeedValue(Double maxSpeed) throws ServiceException {
        if (maxSpeed != null && maxSpeed <= 60.0) {
            return true;
        } else {
            throw new ServiceException("maxSpeed " + maxSpeed + "must be smaller than or equal 60.0");
        }
    }

    private Boolean checkMinSpeedISSmallerThanMaxSpeed(Double minSpeed, Double maxSpeed) throws ServiceException {
        if (minSpeed != null && maxSpeed != null && minSpeed < maxSpeed) {
            return true;
        } else {
            throw new ServiceException("minSpeed " + minSpeed + " must be smaller than maxSpeed " + maxSpeed);
        }
    }
}
