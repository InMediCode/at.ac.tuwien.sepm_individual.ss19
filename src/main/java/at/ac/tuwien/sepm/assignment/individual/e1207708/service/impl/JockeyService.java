package at.ac.tuwien.sepm.assignment.individual.e1207708.service.impl;

import at.ac.tuwien.sepm.assignment.individual.e1207708.service.exceptions.BadRequestException;
import at.ac.tuwien.sepm.assignment.individual.entity.Jockey;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.e1207708.persistence.IJockeyDao;
import at.ac.tuwien.sepm.assignment.individual.e1207708.persistence.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.e1207708.service.IJockeyService;
import at.ac.tuwien.sepm.assignment.individual.e1207708.service.exceptions.ServiceException;
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
    public ArrayList<Jockey> getAllFilteredBy(Jockey jockey) throws ServiceException, BadRequestException {
        LOGGER.info("Get all jockeys filtered by " + jockey);
        try {
            if (jockey.getName() == null) {
                jockey.setName("");
            }
            if (jockey.getSkill() == null) {
                jockey.setSkill(-4.9E-324);
            }
            //validate only skill because name can be ""
            checkSkill(jockey.getSkill());


            return jockeyDao.getAllFilteredBy(jockey);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Jockey insertJockey(Jockey jockey) throws  ServiceException, BadRequestException {
        LOGGER.info("Insert jockey: " + jockey);
        try {
            //validate jockey variables
            checkJockey(jockey);

            return jockeyDao.insertJockey(jockey);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Jockey updateJockey(int id, Jockey jockey) throws  ServiceException, NotFoundException, BadRequestException {
        LOGGER.info("Update jockey " + id + " with " + jockey);

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

    private Boolean checkJockey(Jockey jockey) throws BadRequestException {
        LOGGER.info("Validate Jockey");
        return checkName(jockey.getName()) && checkSkill(jockey.getSkill());
    }

    private Boolean checkName(String name) throws BadRequestException {
        if (name != null && name != "") {
            return true;
        } else {
            LOGGER.error("Problem while validating jockey - name must be set");
            throw new BadRequestException("name must be set");
        }
    }

    private Boolean checkSkill(Double skill) throws BadRequestException {
        if (skill != null && !Double.isInfinite(skill) && !Double.isNaN(skill)) {
            return true;
        } else {
            LOGGER.error("Problem while validating jockey - skill not in range");
            throw  new BadRequestException("skill not in range");
        }
    }
}
