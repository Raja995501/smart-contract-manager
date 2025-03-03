package com.scm.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scm.Entity.User;
import com.scm.Entity.contact;

public interface ContactRepository extends JpaRepository<contact, String> {
    //find contact by user 
    //custom finder method
    // List<contact>findByUser(User user);
    Page<contact>findByUser(User user,Pageable pageable);
   
    //custom query method to get all contact of user
    @Query("SELECT c FROM contact c WHERE c.user.id=:userid")
  List<contact>findByUserId(@Param("userid")String userid);

  Page<contact> findByUserAndNameContaining(User user, String nameKeyword, Pageable pageable);
    Page<contact> findByUserAndEmailContaining(User user, String emailKeyword, Pageable pageable);
    Page<contact> findByUserAndPhoneNumberContaining(User user, String phoneNumberKeyword, Pageable pageable);

}
