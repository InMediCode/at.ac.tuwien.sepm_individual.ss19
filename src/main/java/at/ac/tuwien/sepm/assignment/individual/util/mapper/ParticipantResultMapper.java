package at.ac.tuwien.sepm.assignment.individual.util.mapper;

import at.ac.tuwien.sepm.assignment.individual.entity.ParticipantResult;
import at.ac.tuwien.sepm.assignment.individual.rest.dto.ParticipantResultDto;
import org.springframework.stereotype.Component;

@Component
public class ParticipantResultMapper {
    public ParticipantResultDto entityToDto(ParticipantResult participantResult) {
        return new ParticipantResultDto(participantResult.getId(), participantResult.getRank(), participantResult.getHorseName(), participantResult.getJockeyName(), participantResult.getAvgSpeed(), participantResult.getHorseSpeed(), participantResult.getSkill(), participantResult.getLuckFactor());
    }
}
