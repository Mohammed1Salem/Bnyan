package com.example.bnyan.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDTO {

        private Integer amount; // in SAR
        private String description;
        private String callbackUrl;

        private CardDTO source; // CardDTO info as an object
}

