package com.bookstore.productservice.query.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseModel {

    private String id;

    private String name;

    private String slug;

    private Double price;

    private Double priceEntry;

    private String publisherId;

    private String publisherName;

    private String authorId;

    private String authorName;

    private String productTypeId;

    private String productTypeName;

    private String categories;

    private String categoriesName;

    private Double sale;

    private LocalDate expirationDate;

    private String avatar;

    private int view;

    private int hot;

    private int expiration;

    private int active;

    private String description;

    private String content;

    private int reviewTotal;

    private int reviewStar;

    private int ageReview;

    private int number;

    private int importGoods;

    private int numberImport;

    private String resistant;

    private String energy;

    private String countryCode;

    private int soldQuantity;

    private LocalDate publishedDate;

    private String condition;

    private String binding;

    private LocalDate createdAt;

    private LocalDate updatedAt;
}
