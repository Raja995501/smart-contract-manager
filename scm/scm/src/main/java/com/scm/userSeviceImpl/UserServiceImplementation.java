package com.scm.userSeviceImpl;

import java.util.List;
import java.util.Optional; // Corrected Optional import
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.Entity.User;
import com.scm.helper.Appconstant;
import com.scm.repository.UserRepository;
import com.scm.services.Userservice;

@Service
public class UserServiceImplementation implements Userservice { // Corrected class name

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    private static final Logger logger = LoggerFactory.getLogger(UserServiceImplementation.class); // Corrected logger initialization

    @Override
    public User saveUser(User user) {
        
        String userid= UUID.randomUUID().toString();
        user.setUserId(userid);
        //password Encode
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //set the user role
        user.setRoles(List.of(Appconstant.USER_ROLE));
        logger.info(user.getProvider().toString());
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        logger.info("Fetching user with id: {}", id);
        return userRepository.findById(id); // Implemented getUserById
    }

    @Override
    public User updateUser(User user) {
        logger.info("Updating user: {}", user);

        // Check if the user exists before updating
        Optional<User> existingUserOptional = userRepository.findById(user.getUserId());
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();  // Retrieve the actual User object

            // Update the fields
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            existingUser.setAbout(user.getAbout());
            existingUser.setProfilepic(user.getProfilepic());
            existingUser.setPhoneNum(user.getPhoneNum());
            existingUser.setEnabled(user.isEnabled());
            existingUser.setPhoneVerified(user.isPhoneVerified());
            existingUser.setEmailVerified(user.isEmailVerified());
            existingUser.setProvider(user.getProvider());
            existingUser.setProviderUserId(user.getProviderUserId());

            return userRepository.save(existingUser); // Save the updated user
        } else {
            logger.error("User not found with id: {}", user.getUserId());
            return null; // Or handle appropriately (e.g., throw an exception)
        }
    }

    @Override
    public void deleteUser(User user) {
        logger.info("Deleting user: {}", user);

        userRepository.delete(user); // Implemented deleteUser
    }

    @Override
    public boolean isUserExist(String id) {
        logger.info("Checking if user exists with id: {}", id);
        User user2=userRepository.findById(id).orElse(null);
        return user2!=null?true:false;
    }

    @Override
    public boolean isUserExistByEmail(User user) {
        logger.info("Checking if user exists by email: {}", user.getEmail());
        User user1=userRepository.findById(user.getEmail()).orElse(null);
        return user1!=null?true:false;
    }

    @Override
    public List<User> getAllUser() {
        logger.info("Fetching all users");
        return userRepository.findAll(); // Implemented getAllUser
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        logger.info("Fetching user by email: {}", email);
        return userRepository.findByEmail(email);
    }

 
   
    
    
}