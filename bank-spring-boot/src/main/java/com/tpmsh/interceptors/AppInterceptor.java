package com.tpmsh.BankApp.interceptors;


import com.tpmsh.BankApp.exception.CustomError;
import com.tpmsh.BankApp.helpers.authorization.JwtService;
import com.tpmsh.BankApp.models.User;
import com.tpmsh.BankApp.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class AppInterceptor implements HandlerInterceptor{

    private static final Logger log = LoggerFactory.getLogger(AppInterceptor.class);
    public UserRepository userRepository;

    @Autowired
    public AppInterceptor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private JwtService jwtService = new JwtService();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException, CustomError {
        log.info("In Pre Handle Interceptor Method");
        //TODO: CHECK REQUEST URI:
        if(request.getRequestURI().startsWith("/app") || request.getRequestURI().startsWith("/transact") || request.getRequestURI().startsWith("/logout") || request.getRequestURI().startsWith("/account")){


            //Get Header:
            String header = request.getHeader("Authorization");

            //Check Is Token Included
            if(!jwtService.isTokenIncluded(header))
                throw new CustomError("You need to be logged in.",HttpServletResponse.SC_UNAUTHORIZED);

            log.info("Here is the header: {}", header);
            //Get Access Token From Header
            String token = jwtService.getAccessTokenFromHeader(header);

            //Decode Token
            log.info("Jwt from logout: {}", token);
            Claims claims = jwtService.decodeToken(token);
            String email = claims.getSubject();

            //Get User By Email
            User user = userRepository.getUserDetails(email);

            //Open Session
            request.getSession().setAttribute("user",user);
            request.getSession().setAttribute("token",token);


            //TODO: Get Token Stored int Session:
            log.info("write token {}", request.getSession().getAttribute("token"));

            //TODO: Get User Object Stored In Session:
            log.info("write user {}", request.getSession().getAttribute("user"));

            //TODO: Validate Session Attributes / Objects:
            if(user == null ){
                throw new CustomError("You need to be logged in.",HttpServletResponse.SC_UNAUTHORIZED);
            }
        }

        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        log.info("After Handle Interceptor Method");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.info("After Completion Interceptor Method");
    }
}
