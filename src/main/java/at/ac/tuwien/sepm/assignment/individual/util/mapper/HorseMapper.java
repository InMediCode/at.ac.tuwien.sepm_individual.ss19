package at.ac.tuwien.sepm.assignment.individual.util.mapper;

import at.ac.tuwien.sepm.assignment.individual.rest.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class HorseMapper {

    public HorseDto entityToDto(Horse horse) {
        return new HorseDto(horse.getId(), horse.getName(), horse.getBreed(), horse.getMinSpeed(), horse.getMaxSpeed(), horse.getCreated(), horse.getUpdated());
    }

    public HorseDto[] entityListToDtoArray(ArrayList<Horse> list) {
        HorseDto[] array = new HorseDto[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = this.entityToDto(list.get(i));
        }
        return array;
    }

    public Horse dtoToEntity(HorseDto horseDto) {
        return  new Horse(horseDto.getId(), horseDto.getName(), horseDto.getBreed(), horseDto.getMinSpeed(), horseDto.getMaxSpeed(), horseDto.getCreated(), horseDto.getUpdated());
    }

}
