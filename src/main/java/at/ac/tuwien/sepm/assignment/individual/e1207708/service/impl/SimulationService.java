package at.ac.tuwien.sepm.assignment.individual.e1207708.service.impl;

import at.ac.tuwien.sepm.assignment.individual.e1207708.service.*;
import at.ac.tuwien.sepm.assignment.individual.e1207708.service.exceptions.BadRequestException;
import at.ac.tuwien.sepm.assignment.individual.entity.*;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.e1207708.persistence.ISimulationDao;
import at.ac.tuwien.sepm.assignment.individual.e1207708.persistence.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.e1207708.service.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SimulationService implements ISimulationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimulationService.class);
    private final ISimulationDao simulationDao;
    private final ICalcService calcService;
    private final IParticipantService participantService;
    private  final IHorseService horseService;
    private final IJockeyService jockeyService;

    @Autowired
    public SimulationService(IHorseService horseService, IJockeyService jockeyService, ISimulationDao simulationDao, ICalcService calcService, IParticipantService participantService) {
        this.horseService = horseService;
        this.jockeyService = jockeyService;
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
    public ArrayList<SimulationResult> getAllFilteredBy(String name) throws ServiceException, BadRequestException {
        LOGGER.info("Get all simulations filtered by name " + name);
        try {
            checkName(name);

            ArrayList<SimulationResult> simulationResults = simulationDao.getAllFilteredBy(name);
            return addParticipantsToSimulationList(simulationResults);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public SimulationResult insertSimulation(Simulation simulation) throws NotFoundException, ServiceException, BadRequestException {
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

    private ArrayList<ParticipantResult> getParticipantResult(ArrayList<Participant> participantList) throws ServiceException, NotFoundException, BadRequestException {
        ArrayList<ParticipantResult> list = new ArrayList<>();

        for (Participant participant: participantList) {
            Horse horse = horseService.findOneById(participant.getHorseId());
            Jockey jockey = jockeyService.findOneById(participant.getJockeyId());

            ParticipantResult participantResult = new ParticipantResult();
            participantResult.setHorseName(horse.getName());
            participantResult.setJockeyName(jockey.getName());
            participantResult.setAvgSpeed(calcService.calcAvgSpeed(horse.getMinSpeed(), horse.getMaxSpeed(), jockey.getSkill(), participant.getLuckFactor()));
            participantResult.setHorseSpeed(calcService.calcHorseSpeed(horse.getMinSpeed(), horse.getMaxSpeed(), participant.getLuckFactor()));
            participantResult.setSkill(calcService.calcSkill(jockey.getSkill()));
            participantResult.setLuckFactor(participant.getLuckFactor());

            list.add(participantResult);
        }

        return addParticipantRank(list);
    }

    private ArrayList<ParticipantResult> addParticipantRank(ArrayList<ParticipantResult> participantList) {
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

    private Boolean checkSimulation(Simulation simulation) throws BadRequestException {
        LOGGER.info("Validate Simulation");
        return checkName(simulation.getName()) && checkHorseJockeyCombinations(simulation.getParticipants());
    }

    private Boolean checkName(String name) throws BadRequestException {
        if (name != null && name != "") {
            return true;
        } else {
            LOGGER.error("Problem while validating simulation - name must be set");
            throw new BadRequestException("name must be set");
        }
    }

    private Boolean checkHorseJockeyCombinations(ArrayList<Participant> participants) throws BadRequestException {
        LOGGER.info("Validate Simulation - HorseJockeyCombinations");
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
                LOGGER.error("Problem while validating simulation - horses and jockeys are not allowed to be used multiple times in one simulation");
                throw new BadRequestException("horses and jockeys are not allowed to be used multiple times in one simulation");
            }
        } else {
            LOGGER.error("Problem while validating simulation - horseJockeyCombinations are not allowed to be null");
            throw new BadRequestException("horseJockeyCombinations are not allowed to be null");
        }
    }

    private Boolean checkParticipant(Participant participant) throws BadRequestException {
        if (participant.getHorseId() != null && participant.getJockeyId() != null && participant.getHorseId() !=null) {
            return true;
        } else if (participant.getHorseId() == null) {
            LOGGER.error("Problem while validating participant - horseId must be set");
            throw new BadRequestException("participant horseId must be set");
        } else if (participant.getJockeyId() == null) {
            LOGGER.error("Problem while validating participant - jockeyId must be set");
            throw new BadRequestException("participant jockeyId must be set");
        } else if (participant.getLuckFactor() == null) {
            LOGGER.error("Problem while validating participant - luckFactor must be set");
            throw new BadRequestException("participant luckFactor must be set");
        } else {
            return false;
        }
    }
}
