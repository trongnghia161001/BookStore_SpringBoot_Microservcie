package com.bookstore.commonservice.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionByBillCommand {

    @TargetAggregateIdentifier
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
