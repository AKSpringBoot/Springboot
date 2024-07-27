package com.springboot.app.controller;


import com.springboot.app.model.ErrorResponse;
import com.springboot.app.exception.UserNameExistException;
import com.springboot.app.model.Customer;
import com.springboot.app.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @PostMapping("/api/customers/register")
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer) {
        Customer registeredCustomer = customerService.registerCustomer(customer);
        return ResponseEntity.ok(registeredCustomer);
    }


    @PostMapping("/api/customers/login")
    public ResponseEntity<String> loginCustomer(@RequestParam(name = "uName") String userName, @RequestParam(name = "pwd") String password) {
        Boolean customerOpt = customerService.authenticate(userName, password);
        if (customerOpt) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }


    @ExceptionHandler(value = UserNameExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleCustomerAlreadyExistsException(UserNameExistException ex) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }
}
