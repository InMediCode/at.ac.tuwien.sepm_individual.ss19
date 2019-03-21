package at.ac.tuwien.sepm.assignment.individual.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class SimulationDto {
    private String name;
    @JsonProperty("simulationParticipants")
    private ArrayList<ParticipantDto> participants;

    public SimulationDto() {
    }

    public SimulationDto(String name, ArrayList<ParticipantDto> participants) {
        this.name = name;
        this.participants = participants;
    }

    public String getName() {
        return name;
    }

    public ArrayList<ParticipantDto> getParticipants() {
        return participants;
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
        return Objects.equals(name, simulationDto.name) &&
            Objects.equals(participants, simulationDto.participants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, participants);
    }

    @Override
    public String toString() {
        return "SimulationDto{" +
            "name='" + name + '\'' +
            ", participants=" + (participants == null ? null : Arrays.asList(participants)) +
            '}';
    }
}
