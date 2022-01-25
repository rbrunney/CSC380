package com.example.retrovideogameexchangeapi.endpoint_blls;

import com.example.retrovideogameexchangeapi.models.User;
import com.example.retrovideogameexchangeapi.models.VideoGame;
import com.example.retrovideogameexchangeapi.repositories.UserJPARepository;
import com.example.retrovideogameexchangeapi.repositories.VideoGameJPARepository;
import com.example.retrovideogameexchangeapi.util.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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

    public void deleteVideoGame(String authHead, int id) {
        VideoGame currentVideoGame = videoGameJPA.getById(id);
        User currentUser = userJPA.getByEmailAddress(MyUtils.decodeAuth(authHead)[0]);
        if(currentUser != null) {
            if(currentVideoGame.getUser().equals(currentUser)) {
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
        if(currentVideoGame.getUser().equals(currentUser)) {
            currentVideoGame = videoGameJPA.save(videoGame);
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
        Link linkPOJO = Link.of(POJOLink, "users");
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
