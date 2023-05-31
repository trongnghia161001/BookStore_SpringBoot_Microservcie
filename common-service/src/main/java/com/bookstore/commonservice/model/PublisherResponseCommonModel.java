package com.bookstore.commonservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublisherResponseCommonModel {

    private String id;

    private String name;

    private String address;

    private String phone;

    private String email;

    private String website;

    private String description;

    private Date createdAt;

    private Date updatedAt;

}
