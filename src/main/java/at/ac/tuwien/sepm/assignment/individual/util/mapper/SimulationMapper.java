package at.ac.tuwien.sepm.assignment.individual.util.mapper;

import at.ac.tuwien.sepm.assignment.individual.entity.Participant;
import at.ac.tuwien.sepm.assignment.individual.entity.Simulation;
import at.ac.tuwien.sepm.assignment.individual.rest.dto.ParticipantDto;
import at.ac.tuwien.sepm.assignment.individual.rest.dto.SimulationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SimulationMapper {
    private final ParticipantMapper participantMapper = new ParticipantMapper();

    public SimulationDto entityToDto(Simulation simulation) {
        ArrayList<ParticipantDto> participantDtoList = new ArrayList<ParticipantDto>();

        for (Participant participant: simulation.getParticipants()) {
            participantDtoList.add(participantMapper.entityToDto(participant));
        }

        return new SimulationDto(simulation.getId(), simulation.getName(), participantDtoList);
    }

    public Simulation dtoToEntity(SimulationDto simulationDto) {
        ArrayList<Participant> participantList = new ArrayList<Participant>();

        for (ParticipantDto participantDto: simulationDto.getParticipants()) {
            participantList.add(participantMapper.dtoToEntity(participantDto));
        }

        return new Simulation(simulationDto.getId(), simulationDto.getName(), participantList);
    }
}
