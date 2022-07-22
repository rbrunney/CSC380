package com.example.retrovideogameexchangeapi.endpoint_controllers;

import com.example.retrovideogameexchangeapi.endpoint_blls.UserBLL;
import com.example.retrovideogameexchangeapi.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path="users")
public class UserController {

    @Autowired
    private UserBLL userBLL;

    /////////// Endpoints //////////////////////////////////////

    /**
     * An endpoint for getting user information by a certain id
     * @param authHead A String being pulled of the incoming request to see if user is Authorized
     * @param id An int specifying the desired user they want to find information for
     * @return A User object containing the information about the User
     */
    @GetMapping(path="/{id}")
    @ResponseStatus(code=HttpStatus.OK)
    public User getUser(@RequestHeader(value="Authorization") String authHead, @PathVariable int id) {
        return userBLL.getUserInfo(authHead, id);
    }

    /**
     * An endpoint getting all the users
     * @return A Collection<User> of all the users information
     */
    @GetMapping(path="")
    @ResponseStatus(code=HttpStatus.OK)
    public CollectionModel<User> getUsers() {
        return userBLL.getUsers();
    }

    /**
     * An endpoint for creating a user or "new Account" with the service
     * @param user A User object containing gamesToTrade, currentOffers, name, password, emailAddress, streetAddress
     */
    @PostMapping(path = "")
    @ResponseStatus(code= HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        return userBLL.createUser(user);
    }

    /**
     * An endpoint to delete a user by a given id
     * @param authHead A String being pulled of the incoming request to see if user is Authorized
     * @param id An int specifying the desired user they want to find information for
     */
    @DeleteMapping(path="/{id}")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void deleteUser(@RequestHeader(value="Authorization") String authHead, @PathVariable int id) {
        userBLL.deleteUser(authHead, id);
    }

    /**
     * An endpoint for sending a temporary password if a user's password was ever forgotten
     * @param email A String containing the users email
     */
    @GetMapping(path="/forgotPassword")
    @ResponseStatus(code=HttpStatus.OK)
    public void sendTemporaryPassword(@RequestParam String email) {
        userBLL.forgotPassword(email);
    }

    /**
     * An endpoint for updating a users name
     * @param authHead A String being pulled of the incoming request to see if user is Authorized
     * @param newName A String containing the new name for the user
     * @return A User object containing all the updated information
     */
    @PatchMapping(path="/updateName")
    @ResponseStatus(code=HttpStatus.OK)
    public User changeName(@RequestHeader(value="Authorization") String authHead, @RequestParam String newName) {
        return userBLL.changeName(authHead, newName);
    }

    /**
     * An endpoint for updating a users street address
     * @param authHead A String being pulled of the incoming request to see if user is Authorized
     * @param address A String containing the new address for the user
     * @return A User object containing all the updated information
     */
    @PatchMapping(path="/updateAddress")
    @ResponseStatus(code=HttpStatus.OK)
    public User changeAddress(@RequestHeader(value="Authorization") String authHead, @RequestParam String address) {
        return userBLL.updateAddress(authHead, address);
    }

    /**
     * An endpoint for updating the users password
     * @param authHead A String being pulled of the incoming request to see if user is Authorized
     * @param newPass A String containing the newPassword for the user
     * @return A User object containing all the updated information
     */
    @PatchMapping(path="/changePassword")
    @ResponseStatus(code=HttpStatus.OK)
    public User changePassword(@RequestHeader(value="Authorization") String authHead, @RequestParam String newPass) {
        return userBLL.changePassword(authHead, newPass);
    }


}
