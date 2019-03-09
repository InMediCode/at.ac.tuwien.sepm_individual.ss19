package at.ac.tuwien.sepm.assignment.individual.rest.dto;

import java.util.Arrays;
import java.util.Objects;

public class SimulationDto {
    private Integer id;
    private String name;
    private ParticipantDto[] participants;

    public SimulationDto() {
    }

    public SimulationDto(Integer id, String name, ParticipantDto[] participants) {
        this.id = id;
        this.name = name;
        this.participants = participants;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParticipantDto[] getParticipants() {
        return participants;
    }

    public void setParticipants(ParticipantDto[] participants) {
        this.participants = participants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimulationDto simulationDto = (SimulationDto) o;
        return Objects.equals(id, simulationDto.id) &&
            Objects.equals(name, simulationDto.name) &&
            Arrays.equals(participants, simulationDto.participants);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(id, name);
        result = 31 * result + Arrays.hashCode(participants);
        return result;
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
