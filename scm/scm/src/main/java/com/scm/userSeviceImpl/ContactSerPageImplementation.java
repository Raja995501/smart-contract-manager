package com.scm.userSeviceImpl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.Entity.User;
import com.scm.Entity.contact;
import com.scm.exception.ResourceNotFoundException;
import com.scm.repository.ContactRepository;
import com.scm.services.ContactService;

import jakarta.transaction.Transactional;


@Service
public class ContactSerPageImplementation implements ContactService {
    @Autowired
    private ContactRepository contactRepository;

    @Override
    public contact save(contact contact1) {
       String contactId=UUID.randomUUID().toString();
       contact1.setId(contactId);
       return contactRepository.save(contact1);
    }

    @Override
    public contact update(contact contact1) {
        var contactOld=contactRepository.findById(contact1.getId()).orElseThrow(()->new ResourceNotFoundException("contact not found" ));
        contactOld.setName(contact1.getName());
        contactOld.setEmail(contact1.getEmail());
        contactOld.setPhoneNumber(contact1.getPhoneNumber());
        contactOld.setAddress(contact1.getAddress());
        contactOld.setPicture(contact1.getPicture()); 
        contactOld.setDescription(contact1.getDescription());
        contactOld.setFavorite(contact1.isFavorite());
        contactOld.setWebsiteLink(contact1.getWebsiteLink());
        contactOld.setLinkedlinLink(contact1.getLinkedlinLink());
        
    
        // Save the updated contact
        return contactRepository.save(contactOld);
        
    }

    @Override
    public List<contact> getAll() {
        return contactRepository.findAll();
    }

    @Override
    public contact getById(String id) {
      return contactRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("contact not found" + id));
    }

    @Override
  @Transactional
public void delete(String id) {
   contact cnt=contactRepository.findById(id).orElse(null);
   
   System.out.println();
   System.out.println();
   System.out.println(cnt.getEmail());
   System.out.println();
   cnt.getEmail();
   contactRepository.delete(cnt);
  
}

   

    @Override
    public List<contact> getByUserId(String userId) {
        return contactRepository.findByUserId(userId);
    }

    @Override
    // public List<contact> getByUser(User user) {
    //     return contactRepository.findByUser(user);
    //      }

    //for pagination
    public Page<contact> getByUser(User user, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return contactRepository.findByUser(user, pageable);
    }

    @Override
public Page<contact> serchByName(String name, int size, int page, String sortBy, String order,User user) {
    Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
    Pageable pageable = PageRequest.of(page - 1, size, sort); // Use page-1 to adjust zero-indexing
    return contactRepository.findByUserAndNameContaining(user,name, pageable);
}

@Override
public Page<contact> serchByEmail(String email, int size, int page, String sortBy, String order,User user) {
    Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
    Pageable pageable = PageRequest.of(page - 1, size, sort); // Use page-1
    return contactRepository.findByUserAndEmailContaining(user,email, pageable); // Corrected method call
}

@Override
public Page<contact> serchByPhoneNumber(String phoneNumber, int size, int page, String sortBy, String order,User user) {
    Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
    Pageable pageable = PageRequest.of(page - 1, size, sort); // Use page-1
    return contactRepository.findByUserAndPhoneNumberContaining(user,phoneNumber, pageable); // Corrected method call
}

@Override
public void deleteByid(String id) {
    var con=contactRepository.findById(id).orElse(null);
   
   contactRepository.delete(con);
}


   

}
