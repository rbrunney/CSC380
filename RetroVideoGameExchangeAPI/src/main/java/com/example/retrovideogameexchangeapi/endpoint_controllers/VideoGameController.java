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

    @GetMapping(path="")
    @ResponseStatus(code=HttpStatus.OK)
    public CollectionModel<VideoGame> getVideoGames() {
        return videoGameBLL.getVideoGames();
    }

    @PostMapping(path="")
    @ResponseStatus(code=HttpStatus.CREATED)
    public VideoGame createVideoGame(@RequestHeader(value="Authorization") String authHead, @RequestBody VideoGame newGame) {
        return videoGameBLL.createVideoGame(authHead, newGame);
    }

    @GetMapping(path="/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public VideoGame getVideoGame(@PathVariable int id) {
        return videoGameBLL.getVideoGame(id);
    }

    @DeleteMapping(path="/{id}")
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    public void deleteVideoGame(@RequestHeader(value="Authorization") String authHead, @PathVariable int id) {
        videoGameBLL.deleteVideoGame(authHead, id);
    }
    
    @PatchMapping(path="/{id}")
    @ResponseStatus(code=HttpStatus.OK)
    public VideoGame updateVideoGame(@RequestHeader(value="Authorization") String authHead, @PathVariable int id, @RequestBody VideoGame videoGame) {
        return videoGameBLL.updateVideoGame(authHead, id, videoGame);
    }
}