package com.scm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.scm.Entity.User;
import com.scm.Entity.contact;
import com.scm.helper.Helper;
import com.scm.services.ContactService;
import com.scm.services.ImageService;
import com.scm.services.Userservice;
import com.scm.userform.contactForm;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contact")
public class ContactController {
    @Autowired
    ImageService imageService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private Userservice userservice;
    @RequestMapping("/add")
    public  String addContact(Model model){
        model.addAttribute("title", "addContact");
        contactForm contactform=new contactForm();
        model.addAttribute("contactform", contactform);
       
        return "user/addcontact";
    }

  

  @PostMapping("/add")
    public String saveContact(@Valid @ModelAttribute contactForm contacforn ,BindingResult result,Authentication authentication){
        System.out.println(contacforn);
        System.out.println(contacforn.getPicture().getOriginalFilename());
        //form validation 
        if(result.hasErrors()){
            return "redirect:/user/contact/add";//it not show error because wr redirect
        }

        String userName=Helper.getEmailLoggedEmail(authentication);
        User user=userservice.getUserByEmail(userName).orElseThrow(null);

       // Assuming 'getPicture()' returns a MultipartFile
MultipartFile pictureFile = contacforn.getPicture();

// Upload the image using the image service and get the URL
String fileURL = imageService.uploadImage(pictureFile);
        //contactForm to contact
        contact contact1=new contact();
        contact1.setName(contacforn.getName());
        contact1.setEmail(contacforn.getEmail());
        contact1.setFavorite(contacforn.isFavorite());
        contact1.setAddress(contacforn.getAddress());
        contact1.setDescription(contacforn.getDescription());
        contact1.setPhoneNumber(contacforn.getPhoneNumber());
        contact1.setLinkedlinLink(contacforn.getLinkedlinLink());
        contact1.setWebsiteLink(contacforn.getWebsiteLink());
        contact1.setUser(user);
        contact1.setPicture(fileURL);
        contactService.save(contact1);

        return "redirect:/user/dashboard";
    }

   
    

}
