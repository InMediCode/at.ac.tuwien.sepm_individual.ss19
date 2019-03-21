package at.ac.tuwien.sepm.assignment.individual.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Simulation {
    private String name;
    @JsonProperty("simulationParticipants")
    private ArrayList<Participant> participants;

    public Simulation() {
    }

    public Simulation(String name, ArrayList<Participant> participants) {
        this.name = name;
        this.participants = participants;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Participant> getParticipants() {
        return participants;
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
        return Objects.equals(name, simulation.name) &&
            Objects.equals(participants, simulation.participants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, participants);
    }

    @Override
    public String toString() {
        return "Simulation{" +
            "name='" + name + '\'' +
            ", participants=" + (participants == null ? null : Arrays.asList(participants)) +
            '}';
    }
}
