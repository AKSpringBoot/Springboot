package com.springboot.app.controller;

import com.springboot.app.model.Customer;
import com.springboot.app.service.CustomerService;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {
    @InjectMocks
    private CustomerController customerController;
    @Mock
    private CustomerService customerService;

    private Customer customer;

    @Before
    public void setUp() {
        customer = Customer.builder().Address("Delhi").Doc_no("4125").Name("Test").id(1L).build();
    }

    @Test
    public void registerCustomer() {
        when(customerService.registerCustomer(any())).thenReturn(customer);
        ResponseEntity<Customer> result = customerController.registerCustomer(customer);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Test", result.getBody().getName());
    }

    @Test
    public void loginCustomer() {
        when(customerService.authenticate(anyString(), anyString())).thenReturn(true);
        ResponseEntity<String> result = customerController.loginCustomer("Test", "Test");
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Login successful", result.getBody());
    }

    @Test
    public void loginCustomerFailure() {
        when(customerService.authenticate(anyString(), anyString())).thenReturn(false);
        ResponseEntity<String> result = customerController.loginCustomer("Test", "Test");
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
        assertEquals("Invalid email or password", result.getBody());
    }
}