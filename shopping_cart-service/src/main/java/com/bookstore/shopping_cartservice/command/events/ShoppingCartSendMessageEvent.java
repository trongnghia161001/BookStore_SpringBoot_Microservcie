package com.bookstore.shopping_cartservice.command.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartSendMessageEvent {

    private String id;

    private Long user_id;

    private String message;
}
