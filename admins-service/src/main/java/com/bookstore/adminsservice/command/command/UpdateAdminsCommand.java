package com.bookstore.adminsservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdminsCommand {

    @TargetAggregateIdentifier
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
