package at.ac.tuwien.sepm.assignment.individual.e1207708.rest.dto;

import java.util.Objects;

public class ParticipantDto {
    private Integer horseId;
    private Integer jockeyId;
    private Double luckFactor;

    public ParticipantDto() {
    }

    public ParticipantDto(Integer horseId, Integer jockeyId, Double luckFactor) {
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
        ParticipantDto participantDto = (ParticipantDto) o;
        return Objects.equals(horseId, participantDto.horseId) &&
            Objects.equals(jockeyId, participantDto.jockeyId) &&
            Objects.equals(luckFactor, participantDto.luckFactor);
    }

    @Override
    public int hashCode() {

        return Objects.hash(horseId, jockeyId, luckFactor);
    }

    @Override
    public String toString() {
        return "ParticipantDto{" +
            "horseId=" + horseId +
            ", jockeyId=" + jockeyId +
            ", luckFactor=" + luckFactor +
            '}';
    }
}
