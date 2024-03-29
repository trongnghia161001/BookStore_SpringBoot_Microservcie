package com.bookstore.commonservice.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetDetailsBillQuery {
    private String billId;

    private Long userId;
}
