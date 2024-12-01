package com.raazdk.Ecom.dto;

import lombok.Data;

@Data
public class PaymentInfo {

    private double amount;
    private String recieverEmail;
    private String currency;

}
