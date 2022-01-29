package com.example.retrovideogameexchangeapi.endpoint_blls;

import com.example.retrovideogameexchangeapi.endpoint_controllers.VideoGameController;
import com.example.retrovideogameexchangeapi.models.User;
import com.example.retrovideogameexchangeapi.models.VideoGame;
import com.example.retrovideogameexchangeapi.repositories.UserJPARepository;
import com.example.retrovideogameexchangeapi.repositories.VideoGameJPARepository;
import com.example.retrovideogameexchangeapi.util.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
public class VideoGameBLL {
    ///// Properties //////////////////////////////////////////////////////
    @Autowired
    private VideoGameJPARepository videoGameJPA;
    @Autowired
    private UserJPARepository userJPA;

    public VideoGame createVideoGame(String authHead, VideoGame newGame) {
        User currentUser = userJPA.getByEmailAddress(MyUtils.decodeAuth(authHead)[0]);
        newGame.setUser(currentUser);

        newGame = videoGameJPA.save(newGame);

        for(Link link : generateVideoGamesLinks(newGame.getId())) {
            newGame.add(link);
        }

        return newGame;
    }

    public VideoGame getVideoGame(int id) {
        VideoGame currentVideoGame = videoGameJPA.getFirstById(id);

        for(Link link : generateVideoGamesLinks(currentVideoGame.getId())) {
            currentVideoGame.add(link);
        }

        return currentVideoGame;
    }

    public CollectionModel<VideoGame> getVideoGames() {
        List<VideoGame> videoGames = videoGameJPA.findAll();

        for(VideoGame videoGame : videoGames) {
            for(Link link : generateVideoGamesLinks(videoGame.getId())) {
                videoGame.add(link);
            }
        }

        return CollectionModel.of(videoGames, linkTo(VideoGameController.class).withSelfRel());
    }

    public void deleteVideoGame(String authHead, int id) {
        VideoGame currentVideoGame = videoGameJPA.getById(id);
        User currentUser = userJPA.getByEmailAddress(MyUtils.decodeAuth(authHead)[0]);
        if(currentUser != null) {
            if(currentVideoGame.getUser().getId() == currentUser.getId()) {
                videoGameJPA.delete(currentVideoGame);
            } else {
                throw new SecurityException("Must be the the admin or the user who owns the game to delete it");
            }
        } else if(MyUtils.decodeAuth(authHead)[0].equals("admin")) {
            videoGameJPA.delete(currentVideoGame);
        }
    }

    public VideoGame updateVideoGame(String authHead, int id, VideoGame videoGame) {
        VideoGame currentVideoGame = videoGameJPA.getFirstById(id);
        User currentUser = userJPA.getByEmailAddress(MyUtils.decodeAuth(authHead)[0]);
        if(currentVideoGame.getUser().getId() == currentUser.getId()) {
            videoGame.setId(currentVideoGame.getId());
            videoGame.setUser(currentUser);
            currentVideoGame = videoGame;
            videoGameJPA.save(currentVideoGame);
        } else {
            throw new SecurityException("Cannot update a game that is not yours");
        }

        for(Link link : generateVideoGamesLinks(currentVideoGame.getId())) {
            currentVideoGame.add(link);
        }

        return currentVideoGame;
    }

    public ArrayList<Link> generateVideoGamesLinks(int id) {
        ArrayList<Link> links = new ArrayList<>();

        //Generating Links for User Object
        String selfLink = String.format("http://localhost:8080/videoGames/%s", id);
        String POJOLink = String.format("http://localhost:8080/videoGames/%s", id);
        String offersLink = String.format("http://localhost:8080/videoGames/%s/offers", id);
        String userLink = String.format("http://localhost:8080/videoGames/%s/user", id);

        //Creating Link
        Link linkSelf = Link.of(selfLink, "self");
        Link linkPOJO = Link.of(POJOLink, "videoGames");
        Link linkOffers = Link.of(offersLink, "offers");
        Link linkUser = Link.of(userLink, "user");

        // Adding Links to Array
        links.add(linkSelf);
        links.add(linkPOJO);
        links.add(linkOffers);
        links.add(linkUser);

        return links;
    }
}
