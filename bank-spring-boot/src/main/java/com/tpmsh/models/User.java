package com.tpmsh.BankApp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    private String userId;
    @NotEmpty(message = "The First name field cannot be empty.")
    @Size(min=3, message = "The first name field must greater that 3 characters")
    private String firstName;
    @NotEmpty(message = "The Last name field cannot be empty.")
    @Size(min=3, message = "The first name field must greater that 3 characters")
    private String lastName;
    @Email
    @NotEmpty(message = "Email field cannot be empty.")
    @Pattern(regexp = "([a-zA-Z0-9]+(?:[._+-][a-zA-Z0-9]+)*)@([a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*[.][a-zA-Z]{2,})", message="Please enter a valid email.")
    private String  email;
    @NotEmpty(message = "Password field cannot be empty.")
    @NotNull
    private String  password;
    private String  token;
    private String  code;
    private int verified;
    private LocalDate verifiedAt;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
}
