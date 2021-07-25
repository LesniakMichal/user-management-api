package com.pplflw.usermanagementapi.application.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class AddEmployeeResponse implements Serializable {
    private final UUID id;
}
