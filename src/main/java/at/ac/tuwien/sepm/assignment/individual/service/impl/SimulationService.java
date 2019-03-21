package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.*;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.IHorseDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.IJockeyDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.IParticipantDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.ISimulationDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.service.ICalcService;
import at.ac.tuwien.sepm.assignment.individual.service.ISimulationService;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

@Service
public class SimulationService implements ISimulationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HorseService.class);
    private final IHorseDao horseDao;
    private final IJockeyDao jockeyDao;
    private final ISimulationDao simulationDao;
    private final IParticipantDao participantDao;
    private final ICalcService calcService;

    @Autowired
    public SimulationService(IHorseDao horseDao, IJockeyDao jockeyDao, ISimulationDao simulationDao, IParticipantDao participantDao, ICalcService calcService) {
        this.horseDao = horseDao;
        this.jockeyDao = jockeyDao;
        this.simulationDao = simulationDao;
        this.participantDao = participantDao;
        this.calcService = calcService;
    }

    @Override
    public SimulationResult insertSimulation(Simulation simulation) throws ServiceException {
        //LOGGER.info("Insert Simulation: " + simulation);

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
            try {
                tmpParticipant = participantDao.insertParticipant(simulationId, tmpParticipant);
            } catch (PersistenceException e) {
                throw new ServiceException(e.getMessage(), e);
            }
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
}
