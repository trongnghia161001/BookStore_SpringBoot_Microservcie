package com.bookstore.commonservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionByBillResponseCommonModel {

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
