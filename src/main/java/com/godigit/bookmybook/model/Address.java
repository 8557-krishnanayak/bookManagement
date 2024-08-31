package com.godigit.bookmybook.model;

import com.godigit.bookmybook.dto.AddressDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue
    private int id;
    private String type;
    private String pinCode;
    private String city;
    private String state;

    public Address(AddressDTO addressDTO) {
        this.type = addressDTO.getType();
        this.city = addressDTO.getCity();
        this.state = addressDTO.getState();
        this.pinCode = addressDTO.getPinCode();
    }
}
