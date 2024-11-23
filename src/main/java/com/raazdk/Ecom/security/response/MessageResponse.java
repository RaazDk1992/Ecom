package com.raazdk.Ecom.security.response;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class MessageResponse {
    public MessageResponse(String message) {
        this.message = message;
    }

    private String message;
}

