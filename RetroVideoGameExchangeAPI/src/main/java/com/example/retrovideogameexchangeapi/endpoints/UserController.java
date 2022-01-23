package com.example.retrovideogameexchangeapi.endpoints;

import com.example.retrovideogameexchangeapi.models.User;
import com.example.retrovideogameexchangeapi.models.email.SendMail;
import com.example.retrovideogameexchangeapi.repositories.UserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;
import java.util.Random;

@RestController
@RequestMapping(path="users")
public class UserController {
    @Autowired
    private UserJPARepository userJPA;

    @Autowired
    private UserDetailsManager udm;

    @Autowired
    private PasswordEncoder passEncoder;

    @PostMapping(path = "/createUser")
    @ResponseStatus(code= HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        if (userJPA.getFirstByEmailAddress(user.getEmailAddress()).isPresent()){
            throw new KeyAlreadyExistsException("A user with that email already exists!");
        }
        userJPA.save(user);
        UserDetails newUser = org.springframework.security.core.userdetails.User.withUsername(user.getEmailAddress())
                .password(passEncoder.encode(user.getPassword()))
                .roles("USER").build();
        udm.createUser(newUser);
        for(Link link : generateUserLinks(user.getId())){
            user.add(link); //puts all the generated links into the user
        }
        return user;
    }

//    @GetMapping(path="/{id}/currentOffers")
//    public User filterOffers(@RequestParam Offer.CurrentState status, @PathVariable int id) {
//        return new User();
//    }

    private ArrayList<Link> generateUserLinks(int id){
        ArrayList<Link> links = new ArrayList<>();

        //Generating Links for User Object
        String selfLink = String.format("http://localhost:8080/users/%s", id);
        String POJOLink = String.format("http://localhost:8080/users/%s", id);
        String videoGamesLink = String.format("http://localhost:8080/users/%s/videoGames", id);
        String offersLink = String.format("http://localhost:8080/users/%s/offers", id);

        //Creating Link
        Link linkSelf = Link.of(selfLink, "self");
        Link linkPOJO = Link.of(POJOLink, "users");
        Link linkVideoGames = Link.of(videoGamesLink, "videoGames");
        Link linkOffers = Link.of(offersLink, "offers");

        // Adding Links to Array
        links.add(linkSelf);
        links.add(linkPOJO);
        links.add(linkVideoGames);
        links.add(linkOffers);
        return links;
    }

    @GetMapping(path="/forgotPassword")
    public void sendTemporaryPassword(@RequestParam String username, @RequestParam String email) {
        //Remove Old User From InMemoryUserDetailsManager
        String tempNums = "1234567890";
        String tempLowLetters = "qwertyuiopasdfghjklmnbvcxz";
        String tempUpLetters = "QWERTYUIOPASDFGHJKLZXCVBNM";
        String tempChars = "!@#$%^&*()[]{};:'<>,.\\/?";

        StringBuilder tempPassword = new StringBuilder();
        Random rand = new Random();

        while(tempPassword.length() < 16) {
            tempPassword.append(tempNums.charAt(rand.nextInt(tempNums.length())));
            tempPassword.append(tempLowLetters.charAt(rand.nextInt(tempLowLetters.length())));
            tempPassword.append(tempUpLetters.charAt(rand.nextInt(tempUpLetters.length())));
            tempPassword.append(tempChars.charAt(rand.nextInt(tempChars.length())));
        }


        User currentUser = userJPA.findByNameAndEmailAddress(username, email);
        currentUser.setPassword(tempPassword.toString());
        userJPA.save(currentUser);

        new SendMail(email, "Temporary Password", "Here is your temporary password: " + currentUser.getPassword());
    }
}
