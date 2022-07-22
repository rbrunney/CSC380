package com.example.retrovideogameexchangeapi.endpoint_controllers;

import com.example.retrovideogameexchangeapi.endpoint_blls.VideoGameBLL;
import com.example.retrovideogameexchangeapi.models.VideoGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/videoGames")
public class VideoGameController {

    @Autowired
    private VideoGameBLL videoGameBLL;

    ///// End Points ///////////////////////////////////////////////////////////////

    /**
     * An endpoint for getting all video games within the database
     * @return A Collection<VideoGame> containing all information for a VideoGame
     */
    @GetMapping(path="")
    @ResponseStatus(code=HttpStatus.OK)
    public CollectionModel<VideoGame> getVideoGames() {
        return videoGameBLL.getVideoGames();
    }

    /**
     * An endpoint for creating a VideoGame
     * @param authHead A String being pulled of the incoming request to see if user is Authorized
     * @param newGame A VideoGame object containing a user, offer, name, publisher, publishYear, gamingSystem, gameCondition, and previousOwners
     * @return An VideoGame Object with its current information
     */
    @PostMapping(path="")
    @ResponseStatus(code=HttpStatus.CREATED)
    public VideoGame createVideoGame(@RequestHeader(value="Authorization") String authHead, @RequestBody VideoGame newGame) {
        return videoGameBLL.createVideoGame(authHead, newGame);
    }

    /**
     * An endpoint to get a VideoGame based off a given id
     * @param id An int of the desired VideoGame to find
     * @return A VideoGame object based of what was found by the given id
     */
    @GetMapping(path="/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public VideoGame getVideoGame(@PathVariable int id) {
        return videoGameBLL.getVideoGame(id);
    }

    /**
     * An endpoint to delete a VideoGame bases off a given id
     * @param authHead A String being pulled of the incoming request to see if user is Authorized
     * @param id An int of the desired VideoGame to find
     */
    @DeleteMapping(path="/{id}")
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    public void deleteVideoGame(@RequestHeader(value="Authorization") String authHead, @PathVariable int id) {
        videoGameBLL.deleteVideoGame(authHead, id);
    }

    /**
     * An endpoint to update a VideoGame a user may have
     * @param authHead A String being pulled of the incoming request to see if user is Authorized
     * @param id An int of the desired VideoGame to find
     * @param videoGame A VideoGame object containing a user, offer, name, publisher, publishYear, gamingSystem, gameCondition, and previousOwners
     * @return A VideoGame object with the updated information
     */
    @PatchMapping(path="/{id}")
    @ResponseStatus(code=HttpStatus.OK)
    public VideoGame updateVideoGame(@RequestHeader(value="Authorization") String authHead, @PathVariable int id, @RequestBody VideoGame videoGame) {
        return videoGameBLL.updateVideoGame(authHead, id, videoGame);
    }
}