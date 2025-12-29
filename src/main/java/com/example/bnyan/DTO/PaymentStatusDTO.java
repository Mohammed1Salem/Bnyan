package com.example.bnyan.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentStatusDTO {

    private String id;
    private String status;       // "paid", "pending", "failed"
    private Integer amount;      // in halalas
    private String currency;
    private String description;
}
