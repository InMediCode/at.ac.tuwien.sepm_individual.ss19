package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Jockey;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.IJockeyDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.service.IJockeyService;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JockeyService implements IJockeyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JockeyService.class);
    private final IJockeyDao jockeyDao;

    @Autowired
    public JockeyService(IJockeyDao jockeyDao) { this.jockeyDao = jockeyDao; }

    @Override
    public Jockey findOneById(Integer id) throws ServiceException, NotFoundException {
        LOGGER.info("Get jockey with id " + id);
        try {
            return jockeyDao.findOneById(id);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public ArrayList<Jockey> getAll() throws ServiceException {
        LOGGER.info("Get all jockeys");
        try {
            return jockeyDao.getAll();
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public ArrayList<Jockey> getAllFilteredBy(String name, Double skill) throws ServiceException {
        LOGGER.info("Get all jockeys with name " + name);
        try {
            return jockeyDao.getAllFilteredBy(name, skill);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Jockey insertJockey(Jockey jockey) throws  ServiceException {
        LOGGER.info("Insert jockey: " + jockey);
        try {
            return jockeyDao.insertJockey(jockey);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Jockey updateJockey(int id, Jockey jockey) throws  ServiceException, NotFoundException {
        LOGGER.info("Update jockey " + jockey + "with id " + id);

        try {
            Jockey oldJockey = findOneById(id);

            Boolean updateName = checkName(jockey.getName());
            Boolean updateSkill = checkSkill(jockey.getSkill());

            //update here to be sure no exceptions thrown and that not only a part is updated in DB
            if (updateName) {
                oldJockey.setUpdated(jockeyDao.updateJockeyName(id, jockey.getName()));
                oldJockey.setName(jockey.getName());
            }
            if (updateSkill) {
                oldJockey.setUpdated(jockeyDao.updateJockeySkill(id, jockey.getSkill()));
                oldJockey.setSkill(jockey.getSkill());
            }

            return oldJockey;
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteOneById(Integer id) throws  ServiceException, NotFoundException {
        LOGGER.info("Delete jockey with id " + id);
        try {
            jockeyDao.deleteOneById(id);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private Boolean checkName(String name) throws ServiceException {
        if (name != null && name != "") {
            return true;
        } else {
            throw new ServiceException("name must be set");
        }
    }

    private Boolean checkSkill(Double skill) throws ServiceException {
        if (skill != null && !Double.isInfinite(skill) && !Double.isNaN(skill)) {
            return true;
        } else {
            throw  new ServiceException("skill not in range");
        }
    }
}
