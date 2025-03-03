package com.scm.userform;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class contactForm {
    @NotBlank(message = "Name is Required")
    private String name;
    @Email(message = "Invalid email address")
    private String email;
    @NotBlank(message = "Phone Number is Required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid phone number")
    private String phoneNumber;
    private String address;
    private MultipartFile picture;
    private String picture1;
    private String description;
    private boolean favorite;
    private String websiteLink;
    private String linkedlinLink;
    

}
