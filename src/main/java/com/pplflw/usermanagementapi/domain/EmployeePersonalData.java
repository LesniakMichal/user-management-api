package com.pplflw.usermanagementapi.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EmployeePersonalData {
    private final String firstName;
    private final String lastName;
    private final String login;
}
