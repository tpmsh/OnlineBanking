package com.tpmsh.BankApp.controller_advisor;

import com.tpmsh.BankApp.models.User;
import org.springframework.web.bind.annotation.ModelAttribute;

public class AdvisorController {

    @ModelAttribute("registerUser")
    public User getUserDefaults(){
        return  new User();
    }
}
