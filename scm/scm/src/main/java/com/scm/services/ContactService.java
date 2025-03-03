package com.scm.services;

import com.scm.Entity.User;
import com.scm.Entity.contact;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

public interface ContactService {
    contact save(contact contact1);
    contact update(contact contact1);
    List<contact>getAll();
    contact getById(String id);
    void delete(String id);
    void deleteByid(String id);
    Page<contact>serchByName(String name ,int size,int page,String sortBy,String order,User user);
    Page<contact>serchByEmail(String email,int size,int page,String sortBy,String order,User user);
    Page<contact>serchByPhoneNumber(String phoneNumber,int size,int page,String sortBy,String order,User user);
    List<contact>getByUserId(String userId);
    // List<contact>getByUser(User user);
    //it used for pagination,above return list.
    Page<contact>getByUser(User user,int page,int size,String sortField,String sortDirection);


}
