package com.example.retrovideogameexchangeapi.endpoint_controllers;

import com.example.retrovideogameexchangeapi.models.User;
import com.example.retrovideogameexchangeapi.util.MyUtils;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("admin")
public class AdminController {

    /**
     * An end-point to send a message to RabbitMQ where ADMINS can create event emails.
     * @param emailInfo JSON object containing the receiver email, email subject, and email body.
     */
    @PostMapping("/emailEvent")
    @ResponseStatus(HttpStatus.OK)
    public void emailEvent(@RequestBody JSONObject emailInfo) {
        for(String email : (ArrayList<String>)emailInfo.get("usersToNotify")) {
            HashMap<String, String> message = new HashMap<>();
            message.put("type", "event");
            message.put("email", email);
            message.put("subject", (String) emailInfo.get("subject"));
            message.put("emailBody", (String) emailInfo.get("emailBody"));

            System.out.println(message);

            MyUtils.createQueueMessage("email_queue", message);
        }
    }
}
