package com.bookstore.history_transactionservice.command.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionCreateEvent {

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
