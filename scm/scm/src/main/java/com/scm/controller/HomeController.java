package com.scm.controller;

import com.scm.helper.MessageType;
import com.scm.services.ContactService;
import com.scm.services.ImageService;
import com.scm.services.Userservice;
import com.scm.helper.Helper;
import com.scm.helper.Message;

import com.scm.userform.Userform;
import com.scm.userform.contactForm;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;
import com.scm.Entity.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class HomeController {
    @Autowired
private ContactService contactService;   
@Autowired
private ImageService imageService;

@Autowired
private Userservice userservice;
private contact contact1;
      @GetMapping("/")
    public String index(){
        return "redirect:/home";
    }
    @RequestMapping("/home")
    public String home(Model m) {
        m.addAttribute("title", "home-SCM");
        return "home";
    }
   

    @RequestMapping("/about")
    public String about(Model m) {
        m.addAttribute("title", "about-SCM");
        return "about";
    }

    @RequestMapping("/user/contact")
public String viewContact(
    @RequestParam(value = "page", defaultValue = "1") int page, // Default to page 1 for user experience
    @RequestParam(value = "size", defaultValue = "4") int size,
    @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
    @RequestParam(value = "direction", defaultValue = "asc") String direction,
    Model m, Authentication authentication) {

    m.addAttribute("title", "View-contact-SCM");
    
    String userName = Helper.getEmailLoggedEmail(authentication);
    User user = userservice.getUserByEmail(userName).orElse(null);
    
    // Adjust page number to zero-indexed for Spring Data
    Page<contact> pageContacts = contactService.getByUser(user, page - 1, size, sortBy, direction); // Adjust page index
    m.addAttribute("pageContacts", pageContacts);

    return "contact";
}
@RequestMapping("/user/contacts/search")
public String searchHandler(
    @RequestParam(value = "page", defaultValue = "1") int page,
    @RequestParam("field") String field,
    @RequestParam("keyword") String value,
    @RequestParam(value = "size", defaultValue = "4") int size,
    @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
    @RequestParam(value = "direction", defaultValue = "asc") String direction,
    Model model, Authentication authentication) {

    Page<contact> pageContacts=null;

    // Adjust size to avoid issues with zero size
    if (size < 1) size = 1;
    
    // Get the user from the authentication context
    var user = userservice.getUserByEmail(Helper.getEmailLoggedEmail(authentication)).orElse(null);

    // Handle search based on the field
    if (field.equalsIgnoreCase("name")) {
        pageContacts = contactService.serchByName(value, size, page, sortBy, direction,user );
    } else if (field.equalsIgnoreCase("email")) {
        pageContacts = contactService.serchByEmail(value, size, page, sortBy, direction, user);
    } else if (field.equalsIgnoreCase("phoneNumber")) {
        pageContacts = contactService.serchByPhoneNumber(value, size, page, sortBy, direction, user);
    } 
    else{
        pageContacts = contactService.serchByName(value, size, page, sortBy, direction,user );
    }

    model.addAttribute("pageContacts", pageContacts);
    return "serch"; 
}

    //delete contact
    @RequestMapping("/user/contacts/delete/{contactId}")
    public String deleteB(@PathVariable String contactId){
        contactService.delete(contactId);
        System.out.println("contact id is "+contactId);

        return "redirect:/user/contact";
    }
    
    //update contacts
    @GetMapping("/user/contact/updateInitial/{contactId}")
    public String update(@PathVariable String contactId,Model model){
        contact cnt=contactService.getById(contactId);
        contactForm contactForm1=new contactForm();
        contactForm1.setName(cnt.getName());
        contactForm1.setEmail(cnt.getEmail());
        contactForm1.setAddress(cnt.getAddress());
        contactForm1.setFavorite(cnt.isFavorite());
        contactForm1.setDescription(cnt.getDescription());
        contactForm1.setLinkedlinLink(cnt.getLinkedlinLink());
        contactForm1.setWebsiteLink(cnt.getWebsiteLink());
        contactForm1.setPhoneNumber(cnt.getPhoneNumber());
        contactForm1.setPicture1(cnt.getPicture());
        model.addAttribute("contactform", contactForm1);
        model.addAttribute("contactId",contactId);
        return "updateContactView";
    }
    @PostMapping("/user/contact/update/{contactId}")
public String update(@PathVariable String contactId, @ModelAttribute contactForm contactForm, Model model) {
    // Retrieve the existing contact by ID
    var contact = contactService.getById(contactId);
    
    // Handle the file upload
    MultipartFile pictureFile = contactForm.getPicture();
    
    // Check if the file is not null and not empty
    if (pictureFile != null && !pictureFile.isEmpty()) {
        // Proceed with uploading the image
        String fileURL = imageService.uploadImage(pictureFile);
        contact.setPicture(fileURL);  // Update the picture URL
    } else {
        System.out.println("No file uploaded or file is empty");
        // Optionally handle this by retaining the old picture or showing a message
    }

    // Update the rest of the contact fields from contactForm
    contact.setName(contactForm.getName());
    contact.setEmail(contactForm.getEmail());
    contact.setPhoneNumber(contactForm.getPhoneNumber());
    contact.setAddress(contactForm.getAddress());
    contact.setFavorite(contactForm.isFavorite());
    contact.setDescription(contactForm.getDescription());
    contact.setWebsiteLink(contactForm.getWebsiteLink());
    contact.setLinkedlinLink(contactForm.getLinkedlinLink());

    // Save the updated contact
    contactService.update(contact);

    // Redirect to a specific page after updating
    return "redirect:/user/contact/updateInitial/" + contactId;
}

    
    @RequestMapping("/services")
    public String services(Model m) {
        m.addAttribute("title", "services-SCM");
        return "services";
    }

    @RequestMapping("/login")
    public String login(Model m) {
        m.addAttribute("title", "login-SCM");
        return "login";
    }

    @RequestMapping("/register")
    public String register(Model m) {
        Userform userform = new Userform();
        //sending data from backend
        m.addAttribute("title", "register-SCM");
        m.addAttribute("userform", userform);
        userform.setName("Raja");
        userform.setEmail("Raja@gmail.com");
        userform.setPassword("123");
        userform.setAbout("Ram Ram");
        userform.setPhno("8521510556");
        
        return "register";
    }
//@ModelAttribute Userform userform used for recievs data from form;
//@Valid, BindingResult rBindingResult used for error message in validation
    @PostMapping("/do-register")
    public String doRegister(@Valid @ModelAttribute Userform userform, BindingResult rBindingResult, HttpSession session) {
        System.out.println(userform);

        if (rBindingResult.hasErrors()) {
            System.out.println("Validation errors: " + rBindingResult.getAllErrors());
            return "register";
        }



//        User user = User.builder()
//                .name(userform.getName())
//                .email(userform.getEmail())
//                .password(userform.getPassword())
//                .about(userform.getAbout())
//                .profilepic(userform.getPhno())
//                .profilepic("njdhjd")
//                .build();
        User user =new User();
       
        user.setName(userform.getName());
        user.setEmail(userform.getEmail());
        user.setPassword(userform.getPassword());
        user.setAbout(userform.getAbout());
        user.setPhoneNum(userform.getPhno());
        user.setProfilepic("acs");
        System.out.println(user);

        try {
            userservice.saveUser(user);
            System.out.println(user);
            // Successful registration
            Message message = Message.builder()
                    .content("Registration successful!")
                    .type(MessageType.green)  // You can set different types based on conditions
                    .build();
            session.setAttribute("message", message);
        } catch (Exception e) {
            // Error handling
            Message message = Message.builder()
                    .content("Registration failed! Please try again.")
                    .type(MessageType.red)
                    .build();
            session.setAttribute("message", message);
        }

        return "register";
    }
}
