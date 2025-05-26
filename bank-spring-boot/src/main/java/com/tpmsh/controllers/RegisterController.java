package com.tpmsh.controllers;

import com.tpmsh.helpers.HTML;
import com.tpmsh.helpers.Token;
import com.tpmsh.mailMessenger.MailMessenger;
import com.tpmsh.models.User;
import com.tpmsh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import java.util.*;

@RestController
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult bindingResult, @RequestParam("confirm_password") String confirmPassword) {


        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String password = user.getPassword();

        if(bindingResult.hasErrors() && confirmPassword.isEmpty()){
            List<String> errorMessages = new ArrayList<>();
            for(FieldError error : bindingResult.getFieldErrors()){
                errorMessages.add(error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errorMessages);
        }

        //
        //TODO: CHECK FOR PASSWORD MATCH:
        if(!password.equals(confirmPassword))
            return ResponseEntity.badRequest().body("Şifreler uyuşmuyor.");
        //TODO: GET TOKEN STRING:
        String token = Token.generateToken();

        //TODO: GENERATE RANDOM CODE:
        Random rand = new Random();
        int bound = 123;
        int code = bound * rand.nextInt(bound);

        //TODO: GET EMAIL HTML BODY
        String emailBody = HTML.htmlEmailTemplate(token, Integer.toString(code));
        //TODO: HASH PASSWORD:
        String hashed_password = BCrypt.hashpw(password, BCrypt.gensalt());

        //TODO: REGISTER USER:
        userRepository.registerUser(firstName, lastName, email, hashed_password, token, Integer.toString(code));

        //TODO: SEND EMAIL NOTIFICATION
        try {
            MailMessenger.htmlEmailMessenger("user@beko.com", email, "Verify Account", emailBody);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        //TODO: RETURN REGISTIRATION SUCCESS
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Registration success. Please check your email and verify your account." );
        response.put("user", user);
        return ResponseEntity.ok(response);
    }
}
