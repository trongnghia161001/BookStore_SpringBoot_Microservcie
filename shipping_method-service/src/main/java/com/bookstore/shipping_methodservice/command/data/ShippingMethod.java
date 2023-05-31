package com.bookstore.shipping_methodservice.command.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "shippingMethod")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShippingMethod {

    @Id
    private String id;

    private String name;

    private String description;

    private Date created_at;

    private Date updated_at;
}
