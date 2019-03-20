package at.ac.tuwien.sepm.assignment.individual.rest.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class SimulationDto {
    private Integer id;
    private String name;
    private ArrayList<ParticipantDto> participants;
    //private ParticipantDto[] participants;

    public SimulationDto() {
    }

    public SimulationDto(Integer id, String name, ArrayList<ParticipantDto> participants) {
        this.id = id;
        this.name = name;
        this.participants = participants;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<ParticipantDto> getParticipants() {
        return participants;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParticipants(ArrayList<ParticipantDto> participants) {
        this.participants = participants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimulationDto simulationDto = (SimulationDto) o;
        return Objects.equals(id, simulationDto.id) &&
            Objects.equals(name, simulationDto.name) &&
            Objects.equals(participants, simulationDto.participants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, participants);
    }

    @Override
    public String toString() {
        return "SimulationDto{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", participants=" + (participants == null ? null : Arrays.asList(participants)) +
            '}';
    }
}
