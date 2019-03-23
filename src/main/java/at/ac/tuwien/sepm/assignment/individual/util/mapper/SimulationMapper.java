package at.ac.tuwien.sepm.assignment.individual.util.mapper;

import at.ac.tuwien.sepm.assignment.individual.e1207708.service.exceptions.BadRequestException;
import at.ac.tuwien.sepm.assignment.individual.entity.Participant;
import at.ac.tuwien.sepm.assignment.individual.entity.Simulation;
import at.ac.tuwien.sepm.assignment.individual.e1207708.rest.dto.ParticipantDto;
import at.ac.tuwien.sepm.assignment.individual.e1207708.rest.dto.SimulationDto;
import at.ac.tuwien.sepm.assignment.individual.e1207708.service.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SimulationMapper {
    private final ParticipantMapper participantMapper;

    @Autowired
    public SimulationMapper(ParticipantMapper participantMapper) {
        this.participantMapper = participantMapper;
    }

    public Simulation dtoToEntity(SimulationDto simulationDto) throws BadRequestException {
        if (simulationDto.getParticipants() != null) {
            ArrayList<Participant> participantList = new ArrayList<Participant>();

            for (ParticipantDto participantDto : simulationDto.getParticipants()) {
                participantList.add(participantMapper.dtoToEntity(participantDto));
            }

            return new Simulation(simulationDto.getName(), participantList);
        } else {
            throw new BadRequestException("horseJockeyCombinations are not allowed to be null");
        }
    }
}
