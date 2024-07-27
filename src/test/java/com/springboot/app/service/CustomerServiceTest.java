package com.springboot.app.service;

import com.springboot.app.exception.UserNameExistException;
import com.springboot.app.model.Customer;
import com.springboot.app.repository.CustomerRepository;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;
    @Mock
    private CustomerRepository customerRepository;
    private Customer customer;

    @Before
    public void setUp() {
        customer = Customer.builder().Address("Delhi").Doc_no("4125").Name("Test").id(1L).build();
    }

    @Test
    public void registerCustomer() {
        when(customerRepository.save(any())).thenReturn(customer);
        Customer c1 = customerService.registerCustomer(customer);
        assertEquals(c1.getName(), customer.getName());
    }

    @Test
    public void registerCustomerException() {
        when(customerRepository.existsByUsername(any())).thenReturn(true);
        UserNameExistException userNameExistException = assertThrows(UserNameExistException.class, () ->
                customerService.registerCustomer(customer)
        );
        String expMsg = "Username already exists!!";
        assertEquals(expMsg, userNameExistException.getMessage());
    }

    @Test
    public void authenticateNoUserName() {
        customer.setUsername(StringUtils.EMPTY);
        when(customerRepository.findByUsername(anyString())).thenReturn(customer);
        Boolean response = customerService.authenticate("Test", "Test");
        assertEquals(false, response);
    }

    @Test
    public void authenticateWrongPassword() {
        customer.setPassword("Me");
        when(customerRepository.findByUsername(anyString())).thenReturn(customer);
        Boolean response = customerService.authenticate("Test", "Test");
        assertEquals(false, response);
    }

    @Test
    public void authenticate() {
        customer.setUsername("Me");
        customer.setPassword("Test");
        when(customerRepository.findByUsername(anyString())).thenReturn(customer);
        Boolean response = customerService.authenticate("Me", "Test");
        assertEquals(true, response);
    }
}