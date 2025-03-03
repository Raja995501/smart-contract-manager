package com.scm.userform;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//it is used send data in form from backend
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Userform {
    //validation
    @NotBlank(message = "username is required")
    @Size(min = 3 ,message = "Min 3 character is required")
    private String name;
    @Email(message="Invalid email address")
    @NotBlank(message="Invalid email address")
    private String email;
    @NotBlank(message = "password required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
     @Pattern(
	        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$",
	        message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character (@#$%^&+=!)"
	    )
    private String password;
    @Size(min=8,message="Invalid number")
    private String phno;
    @NotBlank(message="about required")
    @Size(min=10,message = "required more than 10 characters")
    private String about;


}
