package at.ac.tuwien.sepm.assignment.individual.util.mapper;

import at.ac.tuwien.sepm.assignment.individual.entity.Participant;
import at.ac.tuwien.sepm.assignment.individual.rest.dto.ParticipantDto;
import org.springframework.stereotype.Component;

@Component
public class ParticipantMapper {
    public ParticipantDto entityToDto(Participant participant) {
        return new ParticipantDto(participant.getId(), participant.getHorseId(), participant.getJockeyId(), participant.getLuckFactor());
    }

    public Participant dtoToEntity(ParticipantDto participantDto) {
        return new Participant(participantDto.getId(), participantDto.getHorseId(), participantDto.getJockeyId(), participantDto.getLuckFactor());
    }
}
