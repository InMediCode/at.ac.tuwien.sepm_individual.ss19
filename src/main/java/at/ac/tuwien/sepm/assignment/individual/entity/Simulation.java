package at.ac.tuwien.sepm.assignment.individual.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Simulation {
    private Integer id;
    private String name;
    @JsonProperty("simulationParticipants")
    private ArrayList<Participant> participants;

    public Simulation() {
    }

    public Simulation(Integer id, String name, ArrayList<Participant> participants) {
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

    public ArrayList<Participant> getParticipants() {
        return participants;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParticipants(ArrayList<Participant> participants) {
        this.participants = participants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Simulation simulation = (Simulation) o;
        return Objects.equals(id, simulation.id) &&
            Objects.equals(name, simulation.name) &&
            Objects.equals(participants, simulation.participants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, participants);
    }

    @Override
    public String toString() {
        return "Simulation{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", participants=" + (participants == null ? null : Arrays.asList(participants)) +
            '}';
    }
}
