package com.tpmsh.controller_advisor;

import com.tpmsh.models.User;
import org.springframework.web.bind.annotation.ModelAttribute;

public class AdvisorController {

    @ModelAttribute("registerUser")
    public User getUserDefaults(){
        return  new User();
    }
}
