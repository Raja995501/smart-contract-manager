package com.scm.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public class Helper {

    // Get email of logged-in user for the dashboard
    public static String getEmailLoggedEmail(Authentication authentication) {

        // Check if the user is logged in with OAuth2 (e.g., Google, GitHub)
        if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
            DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            String clientId = oauthToken.getAuthorizedClientRegistrationId();

            // For Google login, extract the email
            if (clientId.equalsIgnoreCase("google")) {
                System.out.println("Getting email from Google");
                return oauthUser.getAttribute("email"); // Return the email attribute directly
            }

            // For GitHub login, extract the email and handle null cases
            if (clientId.equalsIgnoreCase("github")) {
                System.out.println("Getting email from GitHub");
                String email = oauthUser.getAttribute("email");

                // If email is null, generate a default one using GitHub login
                if (email == null) {
                    String login = oauthUser.getAttribute("login"); // GitHub username as fallback
                    email = generateDefaultEmail(login);
                    System.out.println("GitHub user email is null. Generated default email: " + email);
                }
                return email;
            }
        }

        // Otherwise, if it's a regular login, return the principal name (e.g., for username/password login)
        return authentication.getName();
    }

    // Fallback method to generate a default email if not provided by GitHub
    private static String generateDefaultEmail(String login) {
        // This method generates a synthetic email based on the GitHub username
        return login + "@github.com";
    }
}
