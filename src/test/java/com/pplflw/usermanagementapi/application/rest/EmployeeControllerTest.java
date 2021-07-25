package com.pplflw.usermanagementapi.application.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pplflw.usermanagementapi.application.request.AddEmployeeRequest;
import com.pplflw.usermanagementapi.application.request.ChangeStatusRequest;
import com.pplflw.usermanagementapi.domain.Employee;
import com.pplflw.usermanagementapi.domain.EmployeeStatus;
import com.pplflw.usermanagementapi.domain.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static com.pplflw.usermanagementapi.domain.EmployeeProvider.getCreatedEmployee;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeRestController.class)
public class EmployeeControllerTest {

    public static final String CONTROLLER_MAPPING = "/employees";
    public static final String CHANGE_STATE_MAPPING = CONTROLLER_MAPPING + "/change-state";
    public static final String GET_STATE_MAPPING = CONTROLLER_MAPPING + "/get-state/{id}";
    @Autowired
    private MockMvc mvc;

    @MockBean
    EmployeeService service;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void testAddUser_withValidRequest_shouldReturnStatus_200() throws Exception {
        Employee employee = getCreatedEmployee();
        given(service.addEmployee(any())).willReturn(employee.getId());
        final String request =
                mapper.writeValueAsString(new AddEmployeeRequest(employee.getFirstName(), employee.getLastName(),
                        employee.getLogin()));

        final MvcResult result = mvc.perform(MockMvcRequestBuilders.post(CONTROLLER_MAPPING)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        final String stringResult = result.getResponse().getContentAsString();

        assertNotNull(stringResult);
        assertTrue(stringResult.contains(employee.getId().toString()));
    }

    @Test
    void testAddUser_withExistingUserData_shouldReturnStatus_409() throws Exception {
        Employee employee = getCreatedEmployee();
        final String exceptionMsg = "Employee with given login already exist.";
        given(service.addEmployee(any())).willThrow(new RuntimeException(exceptionMsg));

        final String request =
                mapper.writeValueAsString(new AddEmployeeRequest(employee.getFirstName(), employee.getLastName(),
                        employee.getLogin()));

        final MvcResult result = mvc.perform(MockMvcRequestBuilders.post(CONTROLLER_MAPPING)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict()).andReturn();

        final String stringResult = result.getResponse().getContentAsString();

        assertNotNull(stringResult);
        assertTrue(stringResult.contains(exceptionMsg));
    }

    @Test
    void testAddUser_requestValidation_withInvalidRequest_shouldReturnStatus_400() throws Exception {
        final String invalidRequest =
                mapper.writeValueAsString(new AddEmployeeRequest(null, null,
                        null));

        final MvcResult result = mvc.perform(MockMvcRequestBuilders.post(CONTROLLER_MAPPING)
                .content(invalidRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        final String stringResult = result.getResponse().getContentAsString();

        assertNotNull(stringResult);
        assertTrue(stringResult.contains("First name should not be empty."));
        assertTrue(stringResult.contains("Last name should not be empty."));
        assertTrue(stringResult.contains("Login should not be empty."));
    }

    @Test
    void testChangeEmployeeStatus_withValidRequest_shouldReturnStatus_200() throws Exception {
        given(service.changeEmployeeStatus(any())).willReturn(EmployeeStatus.IN_CHECK);

        final String request =
                mapper.writeValueAsString(new ChangeStatusRequest(UUID.randomUUID().toString()));

        mvc.perform(MockMvcRequestBuilders.post(CHANGE_STATE_MAPPING)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    void testChangeEmployeeStatus_withInvalidRequest_shouldReturnStatus_400() throws Exception {
        given(service.changeEmployeeStatus(any())).willReturn(EmployeeStatus.IN_CHECK);
        final String request =
                mapper.writeValueAsString(new ChangeStatusRequest(null));

        final MvcResult result = mvc.perform(MockMvcRequestBuilders.post(CHANGE_STATE_MAPPING)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        final String stringResult = result.getResponse().getContentAsString();

        assertNotNull(stringResult);
        assertTrue(stringResult.contains("Id should not be empty."));
    }

    @Test
    void testChangeEmployeeStatus_withNonExistingUser_shouldReturnStatus_409() throws Exception {
        final String exceptionMsg = "Employee with given id doesn't exist.";
        given(service.changeEmployeeStatus(any())).willThrow(new RuntimeException(exceptionMsg));
        final String request =
                mapper.writeValueAsString(new ChangeStatusRequest(UUID.randomUUID().toString()));

        final MvcResult result = mvc.perform(MockMvcRequestBuilders.post(CHANGE_STATE_MAPPING)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict()).andReturn();

        final String stringResult = result.getResponse().getContentAsString();

        assertNotNull(stringResult);
        assertTrue(stringResult.contains(exceptionMsg));
    }

    @Test
    void testGetState_shouldReturnStatus_200() throws Exception {
        given(service.getEmployeeStatus(any())).willReturn(EmployeeStatus.IN_CHECK);
        final String id = UUID.randomUUID().toString();

        final MvcResult result = mvc.perform(MockMvcRequestBuilders.get(GET_STATE_MAPPING, id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        final String stringResult = result.getResponse().getContentAsString();

        String expectedMessage = "User with id:" + id + " has currently status:" + EmployeeStatus.IN_CHECK;
        assertNotNull(stringResult);
        assertTrue(stringResult.contains(expectedMessage));
    }

    @Test
    void testGetState_shouldReturnStatus_209() throws Exception {
        final String exceptionMsg = "Employee with given id doesn't exist.";
        given(service.getEmployeeStatus(any())).willThrow(new RuntimeException(exceptionMsg));
        final String id = UUID.randomUUID().toString();

        final MvcResult result = mvc.perform(MockMvcRequestBuilders.get(GET_STATE_MAPPING, id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict()).andReturn();

        final String stringResult = result.getResponse().getContentAsString();

        assertNotNull(stringResult);
        assertTrue(stringResult.contains(exceptionMsg));
    }
}


