package com.example.retrovideogameexchangeapi.endpoint_controllers;

import com.example.retrovideogameexchangeapi.endpoint_blls.OfferBLL;
import com.example.retrovideogameexchangeapi.models.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="offers")
public class OfferController {

    @Autowired
    private OfferBLL offerBLL;

    ///// Endpoints //////////////////////////

    /**
     * An endpoint to get offers based off of certain Current Status, Pending, Accepted, or Rejected
     * @param filteredStatus Enum value of either Pending, Accepted, or Rejected.
     * @return A Collection<Order> which has orders, based of query for the Current State
     */
    @GetMapping(path="")
    @ResponseStatus(code= HttpStatus.OK)
    public CollectionModel<Offer> getOffer(@RequestParam(required = false) Offer.CurrentState filteredStatus) {
        return offerBLL.getOffers(filteredStatus);
    }

    /**
     * An endpoint to create an offer
     * @param authHead A String being pulled of the incoming request to see if user is Authorized
     * @param offer An Offer object containing offeringUser, receivingUser, requestedVideoGames, currentState, and dateOfferMade
     * @return An Offer object with the updated Current State and Date Offer Made
     */
    @PostMapping(path="")
    @ResponseStatus(code=HttpStatus.OK)
    public Offer createOffer(@RequestHeader(value="Authorization") String authHead, @RequestBody Offer offer) {
        return offerBLL.createOffer(authHead, offer);
    }

    /**
     * An endpoint getting an offer based off of its id
     * @param id An int containing the desired offer
     * @return An offer object with all of its information
     */
    @GetMapping(path="/{id}")
    @ResponseStatus(code=HttpStatus.OK)
    public Offer getOffer(@PathVariable int id) {
        return offerBLL.getOffer(id);
    }

    /**
     * An endpoint for deleting order based of its id
     * @param authHead A String being pulled of the incoming request to see if user is Authorized
     * @param id An int containing the desired offer
     */
    @DeleteMapping(path="/{id}")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void deleteOffer(@RequestHeader(value="Authorization") String authHead, @PathVariable int id) {
        offerBLL.deleteOffer(authHead, id);
    }

    /**
     * An end point for updating an offer based off its id
     * @param authHead A String being pulled of the incoming request to see if user is Authorized
     * @param id An int containing the desired offer
     * @param offer An Offer object containing offeringUser, receivingUser, requestedVideoGames, currentState, and dateOfferMade
     * @return An offer object with all of its updated information
     */
    @PutMapping(path="/{id}")
    @ResponseStatus(code=HttpStatus.OK)
    public Offer updateOffer(@RequestHeader(value="Authorization") String authHead, @PathVariable int id, @RequestBody Offer offer) {
        return offerBLL.updateOffer(authHead, id, offer);
    }
}
