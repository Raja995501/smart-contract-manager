package com.scm.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.Entity.User;

import com.scm.helper.Helper;
import com.scm.services.Userservice;

@ControllerAdvice 
public class RootController {
     @Autowired
    private Userservice userservice;
     @ModelAttribute// call this function ever user url like-profile,dashboard
   public void loggedUserInformation(Model model,Authentication authentication){
    if(authentication==null)return;
            System.out.println("adding looged user information in model");
            // Get the email of the logged-in user using the Helper class
            String email = Helper.getEmailLoggedEmail(authentication);
    
            // Fetch the user based on the email
            User user = userservice.getUserByEmail(email)
                .orElseThrow(null);
               
                if(user==null){

                }else{
                    System.out.println(user);
                }
                model.addAttribute("loggedUser", user);
               
   }}