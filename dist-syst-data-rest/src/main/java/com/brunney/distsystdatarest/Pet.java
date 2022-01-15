package com.brunney.distsystdatarest;

import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Entity
public class Pet {

    public enum Type{Dog, Cat,Fish, Snake, Turtle, Lizard, Chinchilla, Raccoon}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private int age;
    private Type type;

    @ManyToOne
    @JsonIgnore
    private Owner owner;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
