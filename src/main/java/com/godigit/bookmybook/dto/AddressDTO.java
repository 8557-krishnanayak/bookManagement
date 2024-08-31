package com.godigit.bookmybook.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {
    private int id;
    private String type;
    private String pinCode;
    private String city;
    private String state;
}
