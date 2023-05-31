package com.bookstore.history_transactionservice.command.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    private String id;

    private String name;

    private Double price;

    private String avatar;

    private int quantity;

    private Double total;

    private String billId;

    private Long userId;

    private String shoppingCartId;

}
