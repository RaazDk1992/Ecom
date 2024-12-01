package com.raazdk.Ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor

public class OrderSummaryDto {
    String productName;
    int quantity;
    double price;
}
