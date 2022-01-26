package com.example.retrovideogameexchangeapi.endpoint_controllers;

import com.example.retrovideogameexchangeapi.endpoint_blls.OfferBLL;
import com.example.retrovideogameexchangeapi.models.Offer;
import com.example.retrovideogameexchangeapi.util.ViewLevel;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="offers")
public class OfferController {

    @Autowired
    private OfferBLL offerBLL;

    ///// Endpoints //////////////////////////

    @GetMapping(path="")
    @ResponseStatus(code= HttpStatus.OK)
    public CollectionModel<Offer> getOffer(@RequestParam(required = false) Offer.CurrentState filteredStatus) {
        return offerBLL.getOffers(filteredStatus);
    }

    @PostMapping(path="")
    @ResponseStatus(code=HttpStatus.OK)
    public Offer createOffer(@RequestHeader(value="Authorization") String authHead, @RequestBody Offer offer) {
        return offerBLL.createOffer(authHead, offer);
    }

    @GetMapping(path="/{id}")
    @ResponseStatus(code=HttpStatus.OK)
    public Offer getOffer(@PathVariable int id) {
        return offerBLL.getOffer(id);
    }

    @DeleteMapping(path="/{id}")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void deleteOffer(@RequestHeader(value="Authorization") String authHead, @PathVariable int id) {
        offerBLL.deleteOffer(authHead, id);
    }

    @PutMapping(path="/{id}")
    @ResponseStatus(code=HttpStatus.OK)
    public Offer updateOffer(@RequestHeader(value="Authorization") String authHead, @PathVariable int id, @RequestBody Offer offer) {
        return offerBLL.updateOffer(authHead, id, offer);
    }
}
