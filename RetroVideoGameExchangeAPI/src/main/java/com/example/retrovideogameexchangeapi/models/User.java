package com.example.retrovideogameexchangeapi.models;


import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
public class User extends RepresentationModel<User> {

    ///// Properties ///////////////////////
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToMany(mappedBy = "user")
    private List<VideoGame> gamesToTrade = new ArrayList<>();

    @OneToMany(mappedBy = "offeringUser")
    private List<Offer> outboundOffers = new ArrayList<>();

    @OneToMany(mappedBy = "receivingUser")
    private List<Offer> currentOffers = new ArrayList<>();

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String emailAddress;
    @Column(nullable = false)
    private String streetAddress;

    ///// Other Methods /////////////////////

    // *** Validation for Regex
    public Matcher CheckMatch(String line, String regex) {
        return Pattern.compile(regex).matcher(line);
    }

    ///// Constructors //////////////////////

    public User() {

    }

    public User(int id, List<VideoGame> gamesToTrade, List<Offer> currentOffers, String name, String password, String emailAddress, String streetAddress) {
        this.id = id;
        this.gamesToTrade = gamesToTrade;
        this.currentOffers = currentOffers;
        this.name = name;
        this.password = password;
        this.emailAddress = emailAddress;
        this.streetAddress = streetAddress;
    }

    ///// Getters/Setters //////////////////

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<VideoGame> getGamesToTrade() {
        return gamesToTrade;
    }

    public void setGamesToTrade(List<VideoGame> gamesToTrade) {
        this.gamesToTrade = gamesToTrade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name.isEmpty()) {
            throw new NullPointerException("Name was empty");
        } else {
            this.name = name;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        String regexPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()\\[\\]{};:'\"<>,.\\/?]).{8,}$";
        if(CheckMatch(password, regexPassword).find()) {
            this.password = password;
        } else {
            throw new IllegalArgumentException("Password was invalid");
        }
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        String regexEmail = "^\\S+@\\S+\\.\\S+$";

        if(CheckMatch(emailAddress, regexEmail).find()) {
            this.emailAddress = emailAddress;
        } else {
            throw new IllegalArgumentException("Email was invalid");
        }
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        String regexAddress = "^([a-zA-Z]+|[0-9]+)(\\sNorth|\\sEast|\\sSouth|\\sWest|\\sN|\\sE|\\sW|\\sS)?"           //Checks for Street or Number
                + "\\s([a-zA-Z0-9]+(?:[\\s][a-zA-Z0-9]+)*)(\\sNorth|\\sEast|\\sSouth|\\sWest|\\sN|\\sE|\\sW|\\sS)?"   //Checks for Street or Number Again
                + "(,\\sApt\\s[0-9]+|,\\sSuite\\s[0-9]+|,\\s[0-9]+)?"                                                 //Checks for Optional Unit Number
                + "\\n([a-zA-Z]+(?:[\\s-][a-zA-Z]+)*)([,][\\s]?[A-Z]{2})"                                             //Checks for City and State Abbreviation
                + "(\\n[0-9]{5}|\\n[0-9]{5}[-][0-9]{4})?(\\s[0-9]{5}|\\s[0-9]{5}[-][0-9]{4})$";                       //Checks for Zipcode
        if(streetAddress.isEmpty()) {
            throw new NullPointerException("Address was empty");
        }
        if(CheckMatch(streetAddress, regexAddress).find()) {
            this.streetAddress = streetAddress;
        } else {
            throw new IllegalArgumentException("Address was invalid");
        }
    }

    public List<Offer> getCurrentOffers() {
        return currentOffers;
    }

    public void setCurrentOffers(List<Offer> currentOffers) {
        this.currentOffers = currentOffers;
    }

    public List<Offer> getOutboundOffers() {
        return outboundOffers;
    }

    public void setOutboundOffers(List<Offer> outboundOffers) {
        this.outboundOffers = outboundOffers;
    }
}
