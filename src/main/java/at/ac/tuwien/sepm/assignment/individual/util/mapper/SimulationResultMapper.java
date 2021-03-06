package at.ac.tuwien.sepm.assignment.individual.util.mapper;

import at.ac.tuwien.sepm.assignment.individual.entity.ParticipantResult;
import at.ac.tuwien.sepm.assignment.individual.entity.SimulationResult;
import at.ac.tuwien.sepm.assignment.individual.e1207708.rest.dto.ParticipantResultDto;
import at.ac.tuwien.sepm.assignment.individual.e1207708.rest.dto.SimulationResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SimulationResultMapper {
    private final ParticipantResultMapper participantResultMapper;

    @Autowired
    public SimulationResultMapper(ParticipantResultMapper participantResultMapper) {
        this.participantResultMapper = participantResultMapper;
    }

    public SimulationResultDto entityToDto(SimulationResult simulationResult) {
        ArrayList<ParticipantResultDto> participantResultDtoList = new ArrayList<ParticipantResultDto>();

        for (ParticipantResult participantResult: simulationResult.getHorseJockeyCombinations()) {
            participantResultDtoList.add(participantResultMapper.entityToDto(participantResult));
        }

        return new SimulationResultDto(simulationResult.getId(), simulationResult.getName(), simulationResult.getCreated(), participantResultDtoList);
    }

    public SimulationResultDto[] entityListToDtoArray(ArrayList<SimulationResult> list) {
        SimulationResultDto[] array = new SimulationResultDto[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = this.entityToDto(list.get(i));
        }
        return array;
    }
}
