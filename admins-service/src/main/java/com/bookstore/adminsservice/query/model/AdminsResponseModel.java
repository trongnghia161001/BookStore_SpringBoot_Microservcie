package com.bookstore.adminsservice.query.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminsResponseModel {

    private String id;

    private String email;

    private String password;

    private String role;

    private String firstName;

    private String lastName;

    private Boolean enabled = false;

    private String phone;

    private String address;

    private String avatar;

    private Date created_at;

    private Date updated_at;

    private String token;

    private String refreshtoken;

}
