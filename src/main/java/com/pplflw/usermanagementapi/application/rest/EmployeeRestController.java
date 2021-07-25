package com.pplflw.usermanagementapi.application.rest;

import com.pplflw.usermanagementapi.application.request.AddEmployeeRequest;
import com.pplflw.usermanagementapi.application.request.ChangeStatusRequest;
import com.pplflw.usermanagementapi.application.response.AddEmployeeResponse;
import com.pplflw.usermanagementapi.domain.EmployeePersonalData;
import com.pplflw.usermanagementapi.domain.EmployeeStatus;
import com.pplflw.usermanagementapi.domain.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
@Validated
public class EmployeeRestController {
    private final EmployeeService employeeService;

    @Operation(summary = "Adds new employee.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee has been added",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UUID.class))}),
            @ApiResponse(responseCode = "409", description = "User already exist.",
                    content = @Content)})
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    AddEmployeeResponse addEmployee(@Valid @RequestBody final AddEmployeeRequest addEmployeeRequest) {
        log.info("Add employee with login:{}", addEmployeeRequest.getLogin());
        final EmployeePersonalData employeePersonalData =
                new EmployeePersonalData(addEmployeeRequest.getFirstName(), addEmployeeRequest.getLastName(),
                        addEmployeeRequest.getLogin());
        final UUID id = employeeService.addEmployee(employeePersonalData);
        return new AddEmployeeResponse(id);
    }

    @Operation(summary = "Changes state of employee.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee state has been updated"),
            @ApiResponse(responseCode = "409", description = "The employee is already active."),
            @ApiResponse(responseCode = "409", description = "User not found.",
                    content = @Content)})
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(path = "/change-state", consumes = MediaType.APPLICATION_JSON_VALUE)
    void changeEmployeeStatus(@RequestBody @Valid final ChangeStatusRequest changeEmployeeStatusRequest) {
        log.info("Change employee status with login:{}", changeEmployeeStatusRequest.getId());
        employeeService.changeEmployeeStatus(changeEmployeeStatusRequest.getId());
    }

    @Operation(summary = "Show current state of employee.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee state has been updated"),
            @ApiResponse(responseCode = "409", description = "User not found.",
                    content = @Content)})
    @GetMapping("/get-state/{id}")
    ResponseEntity<String> getEmployeeStatus(@PathVariable String id) {
        log.info("Check employee status with id:{}", id);
        final EmployeeStatus userCurrentStatus = employeeService.getEmployeeStatus(id);
        return new ResponseEntity<>(
                "User with id:" + id + " has currently status:" + userCurrentStatus,
                HttpStatus.OK);
    }
}
