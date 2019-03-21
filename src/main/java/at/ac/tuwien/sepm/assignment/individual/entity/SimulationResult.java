package at.ac.tuwien.sepm.assignment.individual.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class SimulationResult {
    private Integer id;
    private String name;
    private LocalDateTime created;
    private ArrayList<ParticipantResult> horseJockeyCombinations;

    public SimulationResult() {
    }

    public SimulationResult(Integer id, String name, LocalDateTime created, ArrayList<ParticipantResult> horseJockeyCombinations) {
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

    public ArrayList<ParticipantResult> getHorseJockeyCombinations() {
        return horseJockeyCombinations;
    }

    public void setHorseJockeyCombinations(ArrayList<ParticipantResult> horseJockeyCombinations) {
        this.horseJockeyCombinations = horseJockeyCombinations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimulationResult that = (SimulationResult) o;
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
        return "SimulationResult{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", created=" + created +
            ", horseJockeyCombinations=" + horseJockeyCombinations +
            '}';
    }
}
