package com.example.retrovideogameexchangeapi.repositories;

import com.example.retrovideogameexchangeapi.models.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface OfferJPARepository extends JpaRepository<Offer, Integer> {

    List<Offer> findByCurrentState(Offer.CurrentState status);
    Offer getFirstById(int id);
}
