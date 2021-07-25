package com.pplflw.usermanagementapi.application.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeStatusRequest implements Serializable {

    @NotEmpty(message = "Id should not be empty.")
    private String id;
}