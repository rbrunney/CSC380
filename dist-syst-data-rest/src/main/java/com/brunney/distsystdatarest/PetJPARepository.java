package com.brunney.distsystdatarest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RestResource
public interface PetJPARepository extends JpaRepository<Pet, Integer> {

    List<Pet> findByAgeGreaterThan(int age);
}
