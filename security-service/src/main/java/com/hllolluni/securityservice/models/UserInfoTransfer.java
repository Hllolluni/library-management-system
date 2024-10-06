package com.hllolluni.securityservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class UserInfoTransfer {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
//    private String role;
}
