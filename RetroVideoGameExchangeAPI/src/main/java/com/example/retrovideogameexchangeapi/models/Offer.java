package com.example.retrovideogameexchangeapi.models;

import net.minidev.json.annotate.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Offer extends RepresentationModel<Offer> {

    ///// Properties /////////////////////////////
    public enum CurrentState{Pending, Accepted, Rejected}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    //*** User requesting for trade
    @ManyToOne()
    @JsonIgnore()
    private User offeringUser;

    @ManyToMany()
    @JoinTable(name = "offered_videoGames", joinColumns = @JoinColumn(name = "videoGame_id"),inverseJoinColumns = @JoinColumn(name = "offer_id"))
    private List<VideoGame> offeredVideoGames = new ArrayList<>();

    //*** User being asked for a trade
    @ManyToOne()
    @JsonIgnore()
    private User receivingUser;

    @ManyToMany()
    @JoinTable(name = "requested_videoGames", joinColumns = @JoinColumn(name = "videoGame_id"),inverseJoinColumns = @JoinColumn(name = "offer_id"))
    private List<VideoGame> requestedVideoGames = new ArrayList<>();

    @Column(nullable = false)
    private CurrentState currentState;

    private String dateOfferMade;

    ///// Other Methods //////////////////////////

    ///// Constructors ///////////////////////////
    public Offer() {

    }

    public Offer(int id, User offeringUser, List<VideoGame> offeredVideoGames, User receivingUser, List<VideoGame> requestedVideoGames, CurrentState currentState) {
        this.id = id;
        this.offeringUser = offeringUser;
        this.offeredVideoGames = offeredVideoGames;
        this.receivingUser = receivingUser;
        this.requestedVideoGames = requestedVideoGames;
        this.currentState = currentState;
    }


    ///// Getter/Setter //////////////////////////

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getOfferingUser() {
        return offeringUser;
    }

    public void setOfferingUser(User offeringUser) {
        this.offeringUser = offeringUser;
    }

    public List<VideoGame> getOfferedVideoGames() {
        return offeredVideoGames;
    }

    public void setOfferedVideoGames(List<VideoGame> offeredVideoGames) {
        this.offeredVideoGames = offeredVideoGames;
    }

    public User getReceivingUser() {
        return receivingUser;
    }

    public void setReceivingUser(User receivingUser) {
        this.receivingUser = receivingUser;
    }

    public List<VideoGame> getRequestedVideoGames() {
        return requestedVideoGames;
    }

    public void setRequestedVideoGames(List<VideoGame> requestedVideoGames) {
        this.requestedVideoGames = requestedVideoGames;
    }

    public CurrentState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(CurrentState currentState) {
        this.currentState = currentState;
    }

    public String getDateOfferMade() {
        return dateOfferMade;
    }

    public void setDateOfferMade() {
        this.dateOfferMade = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss").format(LocalDateTime.now());
    }
}
