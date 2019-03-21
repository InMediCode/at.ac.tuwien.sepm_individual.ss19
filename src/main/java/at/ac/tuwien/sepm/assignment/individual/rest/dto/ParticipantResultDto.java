package at.ac.tuwien.sepm.assignment.individual.rest.dto;

import java.util.Objects;

public class ParticipantResultDto {
    private Integer id;
    private Integer rank;
    private String horseName;
    private String jockeyName;
    private Double avgSpeed;
    private Double horseSpeed;
    private Double skill;
    private Double luckFactor;

    public ParticipantResultDto() {
    }

    public ParticipantResultDto(Integer id, Integer rank, String horseName, String jockeyName, Double avgSpeed, Double horseSpeed, Double skill, Double luckFactor) {
        this.id = id;
        this.rank = rank;
        this.horseName = horseName;
        this.jockeyName = jockeyName;
        this.avgSpeed = avgSpeed;
        this.horseSpeed = horseSpeed;
        this.skill = skill;
        this.luckFactor = luckFactor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getHorseName() {
        return horseName;
    }

    public void setHorseName(String horseName) {
        this.horseName = horseName;
    }

    public String getJockeyName() {
        return jockeyName;
    }

    public void setJockeyName(String jockeyName) {
        this.jockeyName = jockeyName;
    }

    public Double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(Double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public Double getHorseSpeed() {
        return horseSpeed;
    }

    public void setHorseSpeed(Double horseSpeed) {
        this.horseSpeed = horseSpeed;
    }

    public Double getSkill() {
        return skill;
    }

    public void setSkill(Double skill) {
        this.skill = skill;
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
        ParticipantResultDto that = (ParticipantResultDto) o;
        return id.equals(that.id) &&
            rank.equals(that.rank) &&
            horseName.equals(that.horseName) &&
            jockeyName.equals(that.jockeyName) &&
            avgSpeed.equals(that.avgSpeed) &&
            horseSpeed.equals(that.horseSpeed) &&
            skill.equals(that.skill) &&
            luckFactor.equals(that.luckFactor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rank, horseName, jockeyName, avgSpeed, horseSpeed, skill, luckFactor);
    }

    @Override
    public String toString() {
        return "ParticipantResultDto{" +
            "id=" + id +
            ", rank=" + rank +
            ", horseName='" + horseName + '\'' +
            ", jockeyName='" + jockeyName + '\'' +
            ", avgSpeed=" + avgSpeed +
            ", horseSpeed=" + horseSpeed +
            ", skill=" + skill +
            ", luckFactor=" + luckFactor +
            '}';
    }
}
