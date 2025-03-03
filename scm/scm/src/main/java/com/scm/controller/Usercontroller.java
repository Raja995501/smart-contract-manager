package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.Entity.User; // Ensure the correct User entity is imported
import com.scm.helper.Helper;
import com.scm.services.Userservice;

@Controller
@RequestMapping("/user")
public class Usercontroller {

    @Autowired
    private Userservice userservice; 

    private static final Logger logger = LoggerFactory.getLogger(Usercontroller.class);

    // User dashboard page
    @RequestMapping("/dashboard") // get is the default method; this is a get method
    public String userDashBoard(Model model,Authentication authentication) {
        String email = Helper.getEmailLoggedEmail(authentication);
        User user = userservice.getUserByEmail(email)
        .orElseThrow(null);
        String userName = user.getName();       // Fetch the user's name
        String userEmail = user.getEmail();
        model.addAttribute("loggedUser", user);
        model.addAttribute("title", userName+" USER_DASHBOARD");
        return "user/userdashboard";
    }

    // User profile page
    @RequestMapping("/profile")
    public String userProfile(Model model,Authentication authentication) {
        logger.info("Accessing user profile");
       
        // Get the email of the logged-in user using the Helper class
        String email = Helper.getEmailLoggedEmail(authentication);


        // Logging the user's email
        logger.info("Profile accessed by user: {}", email);

        // Fetch the user based on the email
        User user = userservice.getUserByEmail(email)
            .orElseThrow(null);
            String userName = user.getName();       // Fetch the user's name
            String userEmail = user.getEmail();
            String userName1 = (user.getName() != null) ? user.getName() : user.getEmail();

                 // Fetch the user's email
           

            System.out.println(userName);
            System.err.println(userEmail);
            model.addAttribute("loggedUser", user);

            model.addAttribute("title", userName1+" SCM");
           
        // Log the fetched user details (if needed)
        logger.info("User details: {}", user);

        return "user/profile"; // Returning the view for the profile page
    }}