package com.developerblog.ws.ui.model.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Validated
public class UserDetialsRequestModel {

    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 3, max = 50, message = "username must be between 3 and 50")
    private String firstName; //size 3 - 50 & not null
    private String lastName;
    @Email(message = "wrong email format")
    private String email; // email
    @Size(min = 8, max = 16, message = "the password must be between 8 and 16 digits regarding the password criteria")
    @Pattern(regexp =  "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[+-])[A-Za-z0-9+-]+$", message = "password must contain (Aa09-+)")
    private String password; // size 8 - 16 + not null => regex (Aa 09 -+)

}