package com.example.retrovideogameexchangeapi.endpoint_blls;

import com.example.retrovideogameexchangeapi.endpoint_controllers.OfferController;
import com.example.retrovideogameexchangeapi.endpoint_controllers.UserController;
import com.example.retrovideogameexchangeapi.models.Offer;
import com.example.retrovideogameexchangeapi.models.User;
import com.example.retrovideogameexchangeapi.models.email.SendMail;
import com.example.retrovideogameexchangeapi.repositories.UserJPARepository;
import com.example.retrovideogameexchangeapi.util.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
public class UserBLL {

    ///// Properties /////////////////////////////////////////////////////////////////
    @Autowired
    private UserJPARepository userJPA;
    @Autowired
    private UserDetailsManager udm;
    @Autowired
    private PasswordEncoder passEncoder;

    ////// Helper Methods for EndPoints ///////////////////////////////////////////////

    public CollectionModel<User> getUsers() {
        List<User> users = userJPA.findAll();

        for(User user : users) {
            for(Link link : generateUserLinks(user.getId())) {
                user.add(link);
            }
        }

        return CollectionModel.of(users, linkTo(UserController.class).withSelfRel());
    }

    public User getUserInfo(String authHead, int id) {
        User currentUser = userJPA.getByEmailAddress(MyUtils.decodeAuth(authHead)[0]);
        User userInfo = userJPA.getFirstById(id);

        if(currentUser != null) {
            if(!(currentUser.equals(userInfo))) {
                throw new SecurityException("Must be the Admin or the actual user to view user information");
            }

            for(Link link : generateUserLinks(currentUser.getId())) {
                currentUser.add(link);
            }

            return currentUser;
        } else if(MyUtils.decodeAuth(authHead)[0].equals("admin")) {
            for (Link link : generateUserLinks(userInfo.getId())) {
                userInfo.add(link);
            }
        }

        return userInfo;
    }

    public void deleteUser(String authHead, int id) {
        User currentUser = userJPA.getByEmailAddress(MyUtils.decodeAuth(authHead)[0]);
        User userInfo = userJPA.getFirstById(id);

        if(currentUser != null) {
            if(currentUser.equals(userInfo)) {
                udm.deleteUser(MyUtils.decodeAuth(authHead)[0]);
                userJPA.delete(currentUser);
            } else {
                throw new SecurityException("Must be the admin or the user to delete this account");
            }
        } else if(MyUtils.decodeAuth(authHead)[0].equals("admin")) {
            udm.deleteUser(userInfo.getEmailAddress());
            userJPA.delete(userInfo);
        }
    }

    public User createUser(User user) {
        if (userJPA.getFirstByEmailAddress(user.getEmailAddress()).isPresent()){
            throw new KeyAlreadyExistsException("A user with that email already exists!");
        }

        UserDetails newUser = org.springframework.security.core.userdetails.User.withUsername(user.getEmailAddress())
                .password(passEncoder.encode(user.getPassword()))
                .roles("USER").build();
        udm.createUser(newUser);

        user.setPassword(newUser.getPassword());
        userJPA.save(user);
        for(Link link : generateUserLinks(user.getId())){
            user.add(link);
        }
        return user;
    }

    public void forgotPassword(String authHead) {
        String tempNums = "1234567890";
        String tempLowLetters = "qwertyuiopasdfghjklmnbvcxz";
        String tempUpLetters = "QWERTYUIOPASDFGHJKLZXCVBNM";
        String tempChars = "!@#$%^&*()[]{};'<>,.\\/?";

        StringBuilder tempPassword = new StringBuilder();
        Random rand = new Random();
        int passwordLength = rand.nextInt(20) + 12;
        while(tempPassword.length() < passwordLength) {
            tempPassword.append(tempNums.charAt(rand.nextInt(tempNums.length())));
            tempPassword.append(tempLowLetters.charAt(rand.nextInt(tempLowLetters.length())));
            tempPassword.append(tempUpLetters.charAt(rand.nextInt(tempUpLetters.length())));
            tempPassword.append(tempChars.charAt(rand.nextInt(tempChars.length())));
        }

        User currentUser = userJPA.getByEmailAddress(authHead);
        if(currentUser != null) {

            UserDetails newUser = org.springframework.security.core.userdetails.User.withUsername(currentUser.getEmailAddress())
                    .password(passEncoder.encode(tempPassword.toString()))
                    .roles("USER").build();

            udm.updateUser(newUser);

            currentUser.setPassword(newUser.getPassword());
            userJPA.save(currentUser);

            new SendMail(authHead, "Temporary Password", "Here is your temporary password: " + tempPassword);
        }
    }

    public User changeName(String authHead, String newName) {
        User currentUser = userJPA.getByEmailAddress(MyUtils.decodeAuth(authHead)[0]);
        currentUser.setName(newName);

        for(Link link: generateUserLinks(currentUser.getId())) {
            currentUser.add(link);
        }

        return currentUser;
    }

    public User updateAddress(String authHead, String address) {
        User currentUser = userJPA.getByEmailAddress(MyUtils.decodeAuth(authHead)[0]);
        currentUser.setStreetAddress(address);

        for(Link link: generateUserLinks(currentUser.getId())) {
            currentUser.add(link);
        }

        return currentUser;
    }

    public User changePassword(String authHead, String newPass) {
        User currentUser = userJPA.getByEmailAddress(MyUtils.decodeAuth(authHead)[0]);

        UserDetails newUser = org.springframework.security.core.userdetails.User.withUsername(currentUser.getEmailAddress())
                .password(passEncoder.encode(newPass))
                .roles("USER").build();

        udm.updateUser(newUser);
        currentUser.setPassword(newUser.getPassword());
        userJPA.save(currentUser);

        for(Link link : generateUserLinks(currentUser.getId())) {
            currentUser.add(link);
        }

        return currentUser;
    }

    private ArrayList<Link> generateUserLinks(int id){
        ArrayList<Link> links = new ArrayList<>();

        //Generating Links for User Object
        String selfLink = String.format("http://localhost:8080/users/%s", id);
        String POJOLink = String.format("http://localhost:8080/users/%s", id);
        String currentOffersLink = String.format("http://localhost:8080/users/%s/currentOffers", id);
        String outboundOffersLink = String.format("http://localhost:8080/users/%s/outboundOffers", id);
        String gamesToTradeLink = String.format("http://localhost:8080/users/%s/gamesToTrade", id);

        //Creating Link
        Link linkSelf = Link.of(selfLink, "self");
        Link linkPOJO = Link.of(POJOLink, "users");
        Link linkCurrentOffers = Link.of(currentOffersLink, "currentOffers");
        Link linkOutBoundOffers = Link.of(outboundOffersLink, "outboundOffers");
        Link linkGamesToTrade = Link.of(gamesToTradeLink, "gamesToTrade");

        // Adding Links to Array
        links.add(linkSelf);
        links.add(linkPOJO);
        links.add(linkCurrentOffers);
        links.add(linkOutBoundOffers);
        links.add(linkGamesToTrade);
        return links;
    }
}
