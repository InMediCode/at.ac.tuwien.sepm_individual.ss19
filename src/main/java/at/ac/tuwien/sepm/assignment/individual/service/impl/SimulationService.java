package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.*;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.IHorseDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.IJockeyDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.IParticipantDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.ISimulationDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.service.ICalcService;
import at.ac.tuwien.sepm.assignment.individual.service.IParticipantService;
import at.ac.tuwien.sepm.assignment.individual.service.ISimulationService;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Part;
import java.util.*;

@Service
public class SimulationService implements ISimulationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimulationService.class);
    private final IHorseDao horseDao;
    private final IJockeyDao jockeyDao;
    private final ISimulationDao simulationDao;
    private final ICalcService calcService;
    private final IParticipantService participantService;

    @Autowired
    public SimulationService(IHorseDao horseDao, IJockeyDao jockeyDao, ISimulationDao simulationDao, ICalcService calcService, IParticipantService participantService) {
        this.horseDao = horseDao;
        this.jockeyDao = jockeyDao;
        this.simulationDao = simulationDao;
        this.calcService = calcService;
        this.participantService = participantService;
    }

    @Override
    public SimulationResult findOneById(Integer id) throws ServiceException, NotFoundException {
        LOGGER.info("Get simulation with id " + id);
        try {
            SimulationResult simulationResult = simulationDao.findOneById(id);
            return addParticipantsToSimulation(simulationResult);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public ArrayList<SimulationResult> getAll() throws ServiceException {
        LOGGER.info("Get all simulations");
        try {
            ArrayList<SimulationResult> simulationResults = simulationDao.getAll();
            return addParticipantsToSimulationList(simulationResults);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public ArrayList<SimulationResult> getAllFilteredBy(String name) throws ServiceException {
        LOGGER.info("Get all simulations filtered by name " + name);
        try {
            ArrayList<SimulationResult> simulationResults = simulationDao.getAllFilteredBy(name);
            return addParticipantsToSimulationList(simulationResults);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public SimulationResult insertSimulation(Simulation simulation) throws ServiceException {
        LOGGER.info("Insert Simulation: " + simulation);

        //validate simulation values incl. participants
        checkSimulation(simulation);

        SimulationResult simulationResult = new SimulationResult();
        simulationResult.setName(simulation.getName());
        simulationResult.setHorseJockeyCombinations(getParticipantResult(simulation.getParticipants()));

        //insert Simulation into DB
        try {
            simulationResult = simulationDao.insertSimulation(simulationResult);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }

        //insert Participants into DB
        ArrayList<ParticipantResult> participantListWithId = insertParticipantResults(simulationResult.getId(), simulationResult.getHorseJockeyCombinations());
        simulationResult.setHorseJockeyCombinations(participantListWithId);

        return simulationResult;
    }

    private ArrayList<ParticipantResult> insertParticipantResults(int simulationId, ArrayList<ParticipantResult> participantResults) throws ServiceException {
        for (int i = 0; i < participantResults.size(); i++) {
            ParticipantResult tmpParticipant = participantResults.get(i);
            tmpParticipant = participantService.insertParticipant(simulationId, tmpParticipant);

            participantResults.set(i, tmpParticipant);
        }

        return participantResults;
    }

    private ArrayList<ParticipantResult> getParticipantResult(ArrayList<Participant> participantList) throws ServiceException {
        ArrayList<ParticipantResult> list = new ArrayList<>();

        try {
            for (Participant participant: participantList) {
                Horse horse = horseDao.findOneById(participant.getHorseId());
                Jockey jockey = jockeyDao.findOneById(participant.getJockeyId());

                ParticipantResult participantResult = new ParticipantResult();
                participantResult.setHorseName(horse.getName());
                participantResult.setJockeyName(jockey.getName());
                participantResult.setAvgSpeed(calcService.calcAvgSpeed(horse.getMinSpeed(), horse.getMaxSpeed(), jockey.getSkill(), participant.getLuckFactor()));
                participantResult.setHorseSpeed(calcService.calcHorseSpeed(horse.getMinSpeed(), horse.getMaxSpeed(), participant.getLuckFactor()));
                participantResult.setSkill(calcService.calcSkill(jockey.getSkill()));
                participantResult.setLuckFactor(participant.getLuckFactor());

                list.add(participantResult);
            }
        } catch (PersistenceException | NotFoundException e) {
            throw new ServiceException(e.getMessage(), e);
        }

        return addParticipantRank(list);
    }

    private ArrayList<ParticipantResult> addParticipantRank(ArrayList<ParticipantResult> participantList) throws ServiceException {
        participantList.sort(Comparator.comparing(ParticipantResult::getAvgSpeed).reversed());

        int rank = 1;
        ArrayList<ParticipantResult> rankedList = new ArrayList<>();

        for (ParticipantResult participantResult: participantList) {
            ParticipantResult rankedParticipantResult = new ParticipantResult(participantResult.getId(), rank, participantResult.getHorseName(), participantResult.getJockeyName(), participantResult.getAvgSpeed(), participantResult.getHorseSpeed(), participantResult.getSkill(), participantResult.getLuckFactor());
            rankedList.add(rankedParticipantResult);
            rank++;
        }

        return rankedList;
    }

    private ArrayList<SimulationResult> addParticipantsToSimulationList(ArrayList<SimulationResult> list) throws ServiceException {
        for (int i = 0; i < list.size(); i++){
            SimulationResult tmpResult = list.get(i);
            tmpResult = addParticipantsToSimulation(tmpResult);
            list.set(i, tmpResult);
        }

        return list;
    }

    private SimulationResult addParticipantsToSimulation(SimulationResult simulationResult) throws ServiceException {
        int id = simulationResult.getId();

        ArrayList<ParticipantResult> participantResults = participantService.getParticipantResultsBySimulationId(id);
        simulationResult.setHorseJockeyCombinations(participantResults);

        return simulationResult;
    }

    private Boolean checkSimulation(Simulation simulation) throws ServiceException {
        return checkName(simulation.getName()) && checkHorseJockeyCombinations(simulation.getParticipants());
    }

    private Boolean checkName(String name) throws ServiceException {
        if (name != null && name != "") {
            return true;
        } else {
            throw new ServiceException("name must be set");
        }
    }

    private Boolean checkHorseJockeyCombinations(ArrayList<Participant> participants) throws ServiceException {
        if (participants != null) {
            HashSet<Integer> horseControlSet = new HashSet<>();
            HashSet<Integer> jockeyControlSet = new HashSet<>();

            for (Participant participant: participants) {
                horseControlSet.add(participant.getHorseId());
                jockeyControlSet.add(participant.getJockeyId());

                checkParticipant(participant);
            }

            //check for multiple entries
            if (participants.size() == horseControlSet.size() && participants.size() == jockeyControlSet.size()) {
                return true;
            } else {
                throw new ServiceException("horses and jockeys are not allowed to be used multiple times in one simulation");
            }
        } else {
            throw new ServiceException("horseJockeyCombinations are not allowed to be null");
        }
    }

    private Boolean checkParticipant(Participant participant) throws ServiceException {
        if (participant.getHorseId() != null && participant.getJockeyId() != null && participant.getHorseId() !=null) {
            return true;
        } else if (participant.getHorseId() == null) {
            throw new ServiceException("participant horseId must be set");
        } else if (participant.getJockeyId() == null) {
            throw new ServiceException("participant jockeyId must be set");
        } else if (participant.getLuckFactor() == null) {
            throw new ServiceException("participant luckFactor must be set");
        } else {
            return false;
        }
    }
}
