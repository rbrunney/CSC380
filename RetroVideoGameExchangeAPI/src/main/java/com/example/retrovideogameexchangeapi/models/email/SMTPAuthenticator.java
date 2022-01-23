package com.example.retrovideogameexchangeapi.models.email;

import com.example.retrovideogameexchangeapi.models.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.Random;

public class SMTPAuthenticator extends Authenticator {
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(SendMail.senderEmail, SendMail.senderPassword);
    }
}
