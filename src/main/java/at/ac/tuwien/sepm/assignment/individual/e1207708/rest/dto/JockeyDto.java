package at.ac.tuwien.sepm.assignment.individual.e1207708.rest.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class JockeyDto {
    private Integer id;
    private String name;
    private Double skill;
    private LocalDateTime created;
    private LocalDateTime updated;

    public JockeyDto() {
    }

    public JockeyDto(Integer id, String name, Double skill, LocalDateTime created, LocalDateTime updated) {
        this.id = id;
        this.name = name;
        this.skill = skill;
        this.created = created;
        this.updated = updated;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSkill() { return this.skill; }

    public void  setSkill(Double skill) { this.skill = skill; }

    public LocalDateTime getCreated() {
        return this.created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return this.updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JockeyDto)) return false;
        JockeyDto jockeyDto = (JockeyDto) o;
        return Objects.equals(this.id, jockeyDto.id) &&
                Objects.equals(this.name, jockeyDto.name) &&
                Objects.equals(this.skill, jockeyDto.skill) &&
                Objects.equals(this.created, jockeyDto.created) &&
                Objects.equals(this.updated, jockeyDto.updated);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, skill, created, updated);
    }

    @Override
    public String toString() {
        return "JockeyDto{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                ", skill=" + this.skill +
                ", created=" + this.created +
                ", updated=" + this.updated +
                '}';
    }
}
