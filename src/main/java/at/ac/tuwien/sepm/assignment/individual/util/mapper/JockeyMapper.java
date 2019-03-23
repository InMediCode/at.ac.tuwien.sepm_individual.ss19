package at.ac.tuwien.sepm.assignment.individual.util.mapper;

import at.ac.tuwien.sepm.assignment.individual.e1207708.rest.dto.JockeyDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Jockey;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class JockeyMapper {

    public JockeyDto entityToDto(Jockey jockey) {
        return new JockeyDto(jockey.getId(), jockey.getName(), jockey.getSkill(), jockey.getCreated(), jockey.getUpdated());
    }

    public JockeyDto[] entityListToDtoArray(ArrayList<Jockey> list) {
        JockeyDto[] array = new JockeyDto[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = this.entityToDto(list.get(i));
        }
        return array;
    }

    public Jockey dtoToEntity(JockeyDto jockeyDto) {
        return  new Jockey(jockeyDto.getId(), jockeyDto.getName(), jockeyDto.getSkill(), jockeyDto.getCreated(), jockeyDto.getUpdated());
    }
}
