package at.ac.tuwien.sepm.assignment.individual.rest.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class SimulationResultDto {
    private Integer id;
    private String name;
    private LocalDateTime created;
    private List<ParticipantResultDto> horseJockeyCombinations;

    public SimulationResultDto() {
    }

    public SimulationResultDto(Integer id, String name, LocalDateTime created, List<ParticipantResultDto> horseJockeyCombinations) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.horseJockeyCombinations = horseJockeyCombinations;
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

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public List<ParticipantResultDto> getHorseJockeyCombinations() {
        return horseJockeyCombinations;
    }

    public void setHorseJockeyCombinations(List<ParticipantResultDto> horseJockeyCombinations) {
        this.horseJockeyCombinations = horseJockeyCombinations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimulationResultDto that = (SimulationResultDto) o;
        return id.equals(that.id) &&
            name.equals(that.name) &&
            created.equals(that.created) &&
            horseJockeyCombinations.equals(that.horseJockeyCombinations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, created, horseJockeyCombinations);
    }

    @Override
    public String toString() {
        return "SimulationResultDto{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", created=" + created +
            ", horseJockeyCombinationsDto=" + horseJockeyCombinations +
            '}';
    }
}
