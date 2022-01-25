package com.example.retrovideogameexchangeapi.repositories;

import com.example.retrovideogameexchangeapi.models.VideoGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource
public interface VideoGameJPARepository extends JpaRepository<VideoGame, Integer> {

    VideoGame getFirstById(int id);
}
