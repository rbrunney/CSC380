package com.example.retrovideogameexchangeapi.endpoint_controllers;

import com.example.retrovideogameexchangeapi.endpoint_blls.OfferBLL;
import com.example.retrovideogameexchangeapi.models.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="offers")
public class OfferController {

    @Autowired
    private OfferBLL offerBLL;

    ///// Endpoints //////////////////////////

//    @GetMapping(path="")
//    @ResponseStatus(code= HttpStatus.OK)
//    public List<Offer> getOffer(@RequestParam(required = false) Offer.CurrentState filteredStatus) {
//        return offerBLL.getOffers(filteredStatus);
//    }

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
