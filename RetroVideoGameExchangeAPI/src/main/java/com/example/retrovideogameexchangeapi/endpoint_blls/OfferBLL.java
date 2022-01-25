package com.example.retrovideogameexchangeapi.endpoint_blls;

import com.example.retrovideogameexchangeapi.models.Offer;
import com.example.retrovideogameexchangeapi.models.User;
import com.example.retrovideogameexchangeapi.models.VideoGame;
import com.example.retrovideogameexchangeapi.repositories.OfferJPARepository;
import com.example.retrovideogameexchangeapi.repositories.UserJPARepository;
import com.example.retrovideogameexchangeapi.util.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OfferBLL {

    ///// Properties ///////////////////////
    @Autowired
    private OfferJPARepository offerJPA;
    @Autowired
    private UserJPARepository userJPA;

    public Offer getOffer(int id) {
        Offer currentOffer = offerJPA.getFirstById(id);

        for(Link link : generateOfferLinks(id)) {
            currentOffer.add(link);
        }

        return currentOffer;
    }

    public List<Offer> getOffers(Offer.CurrentState filteredStatus) {
        for (Offer.CurrentState status : Offer.CurrentState.values()) {
            if (status == filteredStatus) {
                return offerJPA.findByCurrentState(filteredStatus);
            }
        }

        if(filteredStatus != null) {
            return new ArrayList<>();
        }

        List<Offer> offers = offerJPA.findAll();

        for(Offer offer : offers) {
            for(Link link : generateOfferLinks(offer.getId())) {
                offer.add(link);
            }
        }

        return offers;
    }

    public void deleteOffer(String authHead, int id) {
        Offer currentOffer = offerJPA.getFirstById(id);
        User currentUser = userJPA.getByEmailAddress(MyUtils.decodeAuth(authHead)[0]);

        if(currentUser != null) {
            if(currentOffer.getOfferingUser().equals(currentUser)) {
                offerJPA.delete(currentOffer);
            } else {
                throw new SecurityException("Must be the actual user who made the offer or admin to delete this offer");
            }
        } else if (MyUtils.decodeAuth(authHead)[0].equals("admin")) {
            offerJPA.delete(currentOffer);
        }
    }

    public Offer updateOffer(String authHead, int id, Offer offer) {
        Offer currentOffer = offerJPA.getFirstById(id);
        User currentUser = userJPA.getByEmailAddress(MyUtils.decodeAuth(authHead)[0]);

        if(currentOffer.getOfferingUser().getId() == currentUser.getId()) {
            if(offer.getCurrentState() != Offer.CurrentState.Pending) {
                offer.setCurrentState(Offer.CurrentState.Pending);
            }

            checkOwnership(offer);
            currentOffer = offer;
            offerJPA.save(currentOffer);
        } else if(currentOffer.getReceivingUser().getId() == currentUser.getId()) {
            if(offer.getCurrentState() == Offer.CurrentState.Accepted) {
                User offeringUser = currentOffer.getOfferingUser();

                checkOwnership(offer);

                for(VideoGame game: currentOffer.getOfferedVideoGames()) {
                    game.setUser(currentUser);
                    game.setPreviousOwners(game.getPreviousOwners() + 1);
                }

                for(VideoGame game: currentOffer.getRequestedVideoGames()) {
                    game.setUser(offeringUser);
                    game.setPreviousOwners(game.getPreviousOwners() + 1);
                }

            } else if(offer.getCurrentState() == Offer.CurrentState.Rejected) {
                currentOffer.setCurrentState(offer.getCurrentState());
            }

            currentOffer = offer;
            offerJPA.save(currentOffer);
        }

        for(Link link: generateOfferLinks(currentOffer.getId())) {
            currentOffer.add(link);
        }

        return currentOffer;
    }

    public void checkOwnership(Offer offer) {
        for(VideoGame game : offer.getOfferedVideoGames()) {
            if(!game.getUser().equals(offer.getOfferingUser())) {
                throw new IllegalArgumentException("You are trying to steal a game, shame");
            }
        }

        for(VideoGame game : offer.getRequestedVideoGames()) {
            if(!game.getUser().equals(offer.getReceivingUser())) {
                throw new IllegalArgumentException("You are trying to steal a game, shame");
            }
        }
    }

    private ArrayList<Link> generateOfferLinks(int id){
        ArrayList<Link> links = new ArrayList<>();

        //Generating Links for User Object
        String selfLink = String.format("http://localhost:8080/offers/%s", id);
        String POJOLink = String.format("http://localhost:8080/offers/%s", id);
        String offeredVideoGamesLink = String.format("http://localhost:8080/offers/%s/offeredVideoGames", id);
        String requestedVideoGamesLink = String.format("http://localhost:8080/offers/%s/requestedVideoGames", id);
        String receivingUserLink = String.format("http://localhost:8080/offers/%s/receivingUser", id);
        String offeringUserLink = String.format("http://localhost:8080/offers/%s/offeringUser", id);

        //Creating Link
        Link linkSelf = Link.of(selfLink, "self");
        Link linkPOJO = Link.of(POJOLink, "offers");
        Link linkOfferedVideoGames = Link.of(offeredVideoGamesLink, "offeredVideoGames");
        Link linkRequestedVideoGames = Link.of(requestedVideoGamesLink, "requestedVideoGames");
        Link linkReceivingUser = Link.of(receivingUserLink, "receivingUser");
        Link linkOfferingUser = Link.of(offeringUserLink, "offeringUser");

        // Adding Links to Array
        links.add(linkSelf);
        links.add(linkPOJO);
        links.add(linkOfferedVideoGames);
        links.add(linkRequestedVideoGames);
        links.add(linkReceivingUser);
        links.add(linkOfferingUser);
        return links;
    }
}
