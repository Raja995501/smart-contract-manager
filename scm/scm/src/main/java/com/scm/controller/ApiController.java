package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.Entity.contact;
import com.scm.services.ContactService;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private ContactService contactService;
   @GetMapping("/{contactId}")
public contact getContact(@PathVariable String contactId){
    contact contactData = contactService.getById(contactId);
    contact cnt=new contact();
    cnt.setName(contactData.getName());
    cnt.setEmail(contactData.getEmail());
    cnt.setId(contactId);
    cnt.setAddress(contactData.getAddress());
    cnt.setDescription(contactData.getDescription());
    cnt.setPhoneNumber(contactData.getPhoneNumber());
    cnt.setPicture(contactData.getPicture());
    cnt.setFavorite(contactData.isFavorite());
    cnt.setLinkedlinLink(contactData.getLinkedlinLink());
    cnt.setWebsiteLink(contactData.getWebsiteLink());
    return cnt;
}


}
