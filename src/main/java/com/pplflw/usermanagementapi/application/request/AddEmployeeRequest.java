package com.pplflw.usermanagementapi.application.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@AllArgsConstructor
public class AddEmployeeRequest implements Serializable {

    @NotEmpty(message = "First name should not be empty.")
    private String firstName;

    @NotEmpty(message = "Last name should not be empty.")
    private String lastName;

    @NotEmpty(message = "Login should not be empty.")
    private String login;

}
