package at.ac.tuwien.sepm.assignment.individual.util.mapper;

import at.ac.tuwien.sepm.assignment.individual.rest.dto.JockeyDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Jockey;
import org.springframework.stereotype.Component;

@Component
public class JockeyMapper {

    public JockeyDto entityToDto(Jockey jockey) {
        return new JockeyDto(jockey.getId(), jockey.getName(), jockey.getSkill(), jockey.getCreated(), jockey.getUpdated());
    }
}
