package com.tsvw.model;

import net.formio.validation.constraints.NotEmpty;

import javax.persistence.*;

@Entity(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private long id;

    @NotEmpty
    private String name;

    @ManyToOne(optional = false)
    private Group group;

    // ---------------- constructors ------------------

    public Team(String name, Group group) {
        this.name = name;
        this.group = group;
    }

    public Team() {
    }

    // ---------------- getters and setters ------------------

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
