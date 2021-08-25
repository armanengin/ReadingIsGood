package com.getir.reading.sgood.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Setter(AccessLevel.PRIVATE)
    private int id;

    @NotNull(message = "First name is compulsory")
    @Column(length = 155, name = "first_name")
    private String first_name;

    @NotNull(message = "Last name is compulsory")
    @Column(length = 155, name = "last_name")
    private String last_name;

    @NotNull(message = "Email is compulsory")
    @Email(message = "Email is invalid")
    @Column(length = 155, name = "email")
    private String email;

    @NotNull(message = "Password is compulsory")
    @Length(min = 6, message = "Password should be at least 6 characters")
    @Column(length = 155, name = "password")
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
}
