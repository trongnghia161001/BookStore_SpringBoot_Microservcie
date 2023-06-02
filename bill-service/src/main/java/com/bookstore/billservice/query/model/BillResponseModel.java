package com.bookstore.billservice.query.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillResponseModel {

    private String id;

    private Long userId;

    private Double productCost;

    private Double shippingCost;

    private String paymentMethod;

    private Double totalMoney;

    private String name;

    private String phone;

    private String address;

    private String note;

    private String status;

    private String type;

    private String username;

    private List<TransactionResponseModel> transactionResponseModels;

    private Date createdAt;

    private Date updatedAt;
}
