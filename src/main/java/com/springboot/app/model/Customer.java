package com.springboot.app.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer_details")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name", length = 50, nullable = false)
    private String Name;

    @Column(name = "Address", length = 100, nullable = false)
    private String Address;


    @Column(name = "DOB")
    private LocalDate DoB;

    @Column(name = "Doc_no", length = 50, nullable = false)
    private String Doc_no;

    @Column(name = "username", length = 50, nullable = false, unique = true)
    private String username;


    @Column(name = "password", length = 250)
    private String password;

    @Column(name = "IBAN", length = 50)
    private String Iban;


}