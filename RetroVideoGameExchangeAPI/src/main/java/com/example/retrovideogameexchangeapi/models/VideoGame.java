package com.example.retrovideogameexchangeapi.models;

import net.minidev.json.annotate.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class VideoGame extends RepresentationModel<VideoGame> {
    ///// Properties ////////////////////

    public enum GameCondition {Mint, Good, Fair, Poor}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne()
    @JsonIgnore
    private User user;

    @ManyToMany()
    @JsonIgnore()
    private List<Offer> offers = new ArrayList<>();

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String publisher;
    @Column(nullable = false)
    private int publishYear;
    @Column(nullable = false)
    private String gamingSystem;
    @Column(nullable = false)
    private GameCondition gamingCondition;
    private int previousOwners;

    ///// Constructors /////////////////

    public VideoGame() {

    }

    public VideoGame(int id, User user, List<Offer> offer, String name, String publisher, int publishYear, String gamingSystem, GameCondition gamingCondition, int previousOwners) {
        this.id = id;
        this.user = user;
        this.offers = offer;
        this.name = name;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.gamingSystem = gamingSystem;
        this.gamingCondition = gamingCondition;
        this.previousOwners = previousOwners;
    }

    ///// Getter/ Setters /////////////

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name.isEmpty()) {
            throw new NullPointerException("Name was empty");
        }
        this.name = name;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        if(publisher.isEmpty()) {
            throw new NullPointerException("Publisher was empty");
        }
        this.publisher = publisher;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        if(publishYear < 1950) {
            throw new IllegalArgumentException("Publish Year was invalid. Enter a valid above 1950");
        }
        this.publishYear = publishYear;
    }

    public String getGamingSystem() {
        return gamingSystem;
    }

    public void setGamingSystem(String gamingSystem) {
        if(gamingSystem.isEmpty()) {
            throw new NullPointerException("Gaming System was empty");
        }
        this.gamingSystem = gamingSystem;
    }

    public GameCondition getGamingCondition() {
        return gamingCondition;
    }

    public void setGamingCondition(GameCondition gamingCondition) {
        this.gamingCondition = gamingCondition;
    }

    public int getPreviousOwners() {
        return previousOwners;
    }

    public void setPreviousOwners(int previousOwners) {
        if(previousOwners < 0) {
            throw new IllegalArgumentException("Previous Owners cannot be less than zero");
        }
        this.previousOwners = previousOwners;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}
