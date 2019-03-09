package at.ac.tuwien.sepm.assignment.individual.entity;

import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Jockey {
    private Integer id;
    private String name;
    private Double skill;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Boolean deleted;

    public Jockey() {
    }

    public Jockey(Integer id, String name, Double skill, LocalDateTime created, LocalDateTime updated) {
        this.id = id;
        this.name = name;
        this.skill = skill;
        this.created = created;
        this.updated = updated;
        this.deleted = false;
    }

    public Jockey(Integer id, String name, Double skill, LocalDateTime created, LocalDateTime updated, Boolean deleted) {
        this.id = id;
        this.name = name;
        this.skill = skill;
        this.created = created;
        this.updated = updated;
        this.deleted = deleted;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Jockey)) return false;
        Jockey jockey = (Jockey) o;
        return Objects.equals(this.id, jockey.id) &&
                Objects.equals(this.name, jockey.name) &&
                Objects.equals(this.skill, jockey.skill) &&
                Objects.equals(this.created, jockey.created) &&
                Objects.equals(this.updated, jockey.updated) &&
                Objects.equals(deleted, jockey.deleted);
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
                ", deleted=" + deleted +
                '}';
    }
}
