package com.scm.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.scm.userSeviceImpl.SecurityCustomdetailsService;

@Configuration
public class SecurityConfig {

    // @Bean
    // public UserDetailsService userDetailsService() {
        //org.springframework.security.core.userdetails.User .this is not com.scm.entity user
        // Create UserDetails with default password encoder
    //     UserDetails user1 = User
    //             .withDefaultPasswordEncoder()  // Fixed method name
    //             .username("Raja")
    //             .password("123")
    //             .roles("ADMIN", "USER")  // Corrected roles
    //             .build();
        
    //     // Create InMemoryUserDetailsManager and add the user
    //     InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1);  // Add user
    //     return inMemoryUserDetailsManager;
    // }

    //the current configuration is designed to create users and passwords in memory, not from a database. The InMemoryUserDetailsManager is an implementation of UserDetailsService that stores user details directly in memory, which is useful for simple cases or testing purposes.
   @Autowired
    OAuthAuthenticationSuccessfull handler;
    @Autowired
    SecurityCustomdetailsService securityCustomdetailsService; 
    @Bean
    public AuthenticationProvider authenticationProvider(){

        DaoAuthenticationProvider  daoAuthenticationProvider=new DaoAuthenticationProvider();
        //userdetail  ka object
        daoAuthenticationProvider.setUserDetailsService(securityCustomdetailsService);
        //  password encoder ka objeect
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;

    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        //configuration 
        //url config kiya hai kaun private aur kaun public hoga
        httpSecurity.authorizeHttpRequests(authorize->{
            // authorize.requestMatchers("/home","/register").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
                  
        });
         //form default login
   
      //agar hume kuchh nhi karna huaa to yaha aayenge form login se related
//it is used when we use default login page of spring boot
     
//  httpSecurity.formLogin(Customizer.withDefaults());

//it is used when we use our login page
      httpSecurity.formLogin(formLogin->{
        formLogin.loginPage("/login");
        formLogin.loginProcessingUrl("/authenticate");
        formLogin.successForwardUrl("/user/dashboard");
        // formLogin.failureForwardUrl("/login?error=true");
        formLogin.usernameParameter("email");
        formLogin.passwordParameter("password");
      });
      //it is used for logout
      httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(logoutform->{
          logoutform.logoutUrl("/do-logout");
          logoutform.logoutSuccessUrl("/login?error=true");
        });

        //oath2 configurataion(login with google)
     
       // httpSecurity.oauth2Login(Customizer.withDefaults()); //  it is used for default
       httpSecurity.oauth2Login(oauth->{
        oauth.loginPage("/login");
        oauth.successHandler(handler);
       });
        return httpSecurity.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
