package com.example.bnyan.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {

    private String name;

    private String number;

    private String cvc;

    private String month;

    private String year;

}
