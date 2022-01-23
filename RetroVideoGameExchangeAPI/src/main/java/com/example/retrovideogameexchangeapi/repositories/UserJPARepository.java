package com.example.retrovideogameexchangeapi.repositories;

import com.example.retrovideogameexchangeapi.models.Offer;
import com.example.retrovideogameexchangeapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource()
public interface UserJPARepository extends JpaRepository<User, Integer> {

    Optional<User> getFirstByEmailAddress(String emailAddress);
    User findByNameAndEmailAddress(String username, String password);
}
