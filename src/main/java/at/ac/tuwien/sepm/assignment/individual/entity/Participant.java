package at.ac.tuwien.sepm.assignment.individual.entity;

import java.util.Objects;

public class Participant {
    private Integer horseId;
    private Integer jockeyId;
    private Double luckFactor;

    public Participant() {
    }

    public Participant(Integer horseId, Integer jockeyId, Double luckFactor) {
        this.horseId = horseId;
        this.jockeyId = jockeyId;
        this.luckFactor = luckFactor;
    }

    public Integer getHorseId() {
        return horseId;
    }

    public void setHorseId(Integer horseId) {
        this.horseId = horseId;
    }

    public Integer getJockeyId() {
        return jockeyId;
    }

    public void setJockeyId(Integer jockeyId) {
        this.jockeyId = jockeyId;
    }

    public Double getLuckFactor() {
        return luckFactor;
    }

    public void setLuckFactor(Double luckFactor) {
        this.luckFactor = luckFactor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant participant = (Participant) o;
        return Objects.equals(horseId, participant.horseId) &&
            Objects.equals(jockeyId, participant.jockeyId) &&
            Objects.equals(luckFactor, participant.luckFactor);
    }

    @Override
    public int hashCode() {

        return Objects.hash(horseId, jockeyId, luckFactor);
    }

    @Override
    public String toString() {
        return "Participant{" +
            "horseId=" + horseId +
            ", jockeyId=" + jockeyId +
            ", luckFactor=" + luckFactor +
            '}';
    }
}