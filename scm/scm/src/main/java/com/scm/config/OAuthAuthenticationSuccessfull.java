package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.Entity.User;
import com.scm.Entity.providers;
import com.scm.helper.Appconstant;
import com.scm.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessfull implements AuthenticationSuccessHandler {

    @Autowired
    UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessfull.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // Custom logic for handling successful authentication
        logger.info("User authenticated successfully: {}", authentication.getName());

        // Check if the authentication is an OAuth2 token
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

            // Extract user details from the OAuth2 token
            Map<String, Object> userDetails = oauthToken.getPrincipal().getAttributes();
            String provider = oauthToken.getAuthorizedClientRegistrationId(); // Fetch the provider name (google, github)

            User user = new User();
            user.setUserId(UUID.randomUUID().toString());
            user.setPassword(""); // No password as it's OAuth2
            user.setEmailVerified(true);
            user.setEnabled(true);
            user.setProviderUserId(UUID.randomUUID().toString());

            // Common user details for Google
            if (provider.equalsIgnoreCase("google")) {
                String name = (String) userDetails.get("name");
                String email = (String) userDetails.get("email");
                String picture = (String) userDetails.get("picture");

                user.setName(name);
                user.setEmail(email);
                user.setProfilepic(picture);
                user.setProvider(providers.GOOGLE);
            }
            // Common user details for GitHub
            else if (provider.equalsIgnoreCase("github")) {
                String name = (String) userDetails.get("name");
                String login = (String) userDetails.get("login"); 
                String email = (String) userDetails.get("email");
                if (email == null) {
                    email = generateDefaultEmail(login);
                    logger.warn("GitHub user email is null. Generated default email: {}", email);
                }
                
                String picture = (String) userDetails.get("avatar_url"); // Use avatar_url for GitHub

                user.setName(name);
                user.setEmail(email);
                user.setProfilepic(picture);
                user.setProvider(providers.GITHUB);
            } else {
                logger.error("Unknown provider: {}", provider);
                response.sendRedirect("/login?error=unknownProvider");
                return;
            }

            // Log user details
            logger.info("User authenticated successfully: {}", userDetails.get("name"));
            logger.info("User email: {}", user.getEmail());

            // Check if the user already exists
            User existingUser = userRepository.findByEmail(user.getEmail()).orElse(null);
            if (existingUser == null) {
                userRepository.save(user);
                logger.info("User saved: {}", user.getEmail());
            } else {
                logger.info("User already exists: {}", user.getEmail());
            }

            // Redirect user after successful login
            response.sendRedirect("/user/dashboard");
        } 
    }
    private String generateDefaultEmail(String login) {
        String sanitizedLogin = login.toLowerCase().replaceAll("[^a-z0-9]", ""); // Sanitize login to create a valid email
        return sanitizedLogin + "@github.com"; // Use GitHub as the domain
    }
}
