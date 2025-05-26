package com.tpmsh.controllers;

import com.tpmsh.helpers.Token;
import com.tpmsh.helpers.authorization.JwtService;
import com.tpmsh.models.User;
import com.tpmsh.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class AuthController {


    private UserRepository userRepository;


    public JwtService jwtService;

    @Autowired
    public AuthController(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }





    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> requestMap ,
                                   HttpSession session, HttpServletResponse response){

        String email = requestMap.get("email");
        String password = requestMap.get("password");




        //TODO: VALIDATE INPUT FIELDS / FORM DATA:
        if(email.isEmpty() || email== null || password.isEmpty() || password == null){
            return ResponseEntity.badRequest().body("Username or Password Cannot Be Empty.");
        }
        //TODO: CHECK IF EMAIL EXIST:
        String getEmailInDatabase = userRepository.getUserEmail(email);

        //Check If Email Exists:
        if(getEmailInDatabase != null)
        {
            //Get Password In Database:
            String getPasswordInDatabase = userRepository.getUserPassword(getEmailInDatabase);

            //Validate Password:
            if(!BCrypt.checkpw(password,getPasswordInDatabase)){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect Username or Password");
            }
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }


        //TODO: CHECK IF USER ACCOUNT IS VERIFIED:
        int verified = userRepository.isVerified(getEmailInDatabase);

        if(verified !=1){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Account verification required.");
        }


        //TODO: PROCEED TO LOG THE USER IN:
        User user = userRepository.getUserDetails(getEmailInDatabase);

        String jwt = jwtService.generateToken(user.getEmail());
        log.info("Jwt from login: {}", jwt);
        log.info(String.valueOf(jwtService.decodeToken(jwt)));
        String token = Token.generateToken();

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "Authentication confirmed" );
        responseBody.put("access_token", jwt);




        session.setAttribute("user", user);
        log.info(String.valueOf(user));
        session.setAttribute("token", jwt);
        session.setAttribute("authenticated", true);




        return ResponseEntity.ok(responseBody);


    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session){

        log.info("Logged out successfully." + session.getAttribute("token"));
        session.invalidate();

        return ResponseEntity.ok("Logged out successfully.");

    }
}
