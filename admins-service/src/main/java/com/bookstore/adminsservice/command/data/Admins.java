package com.bookstore.adminsservice.command.data;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "admins")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Admins {

    @Id
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
}
