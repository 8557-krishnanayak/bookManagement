package com.godigit.bookmybook.converstion;


import com.godigit.bookmybook.dto.CartDto;
import com.godigit.bookmybook.dto.UserDTO;
import com.godigit.bookmybook.model.CartModel;
import com.godigit.bookmybook.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class UserConverter {

    public static UserDTO toDTO(UserModel user) {
        UserDTO dto = new UserDTO();

        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setRole(user.getRole());
        dto.setPassword(user.getPassword());
        dto.setBirthDate(user.getBirthDate());
        dto.setRegisteredDate(user.getRegisteredDate());
        dto.setUpdateDate(user.getUpdateDate());

        List<CartModel> cart = user.getCart() == null ? new ArrayList<>() : user.getCart();
        List<CartDto> list = cart.stream().map(CartConvertor::toDTO).toList();

        dto.setCart(list);

        return dto;
    }

    public static UserModel toEntity(UserDTO dto) {

        if(dto == null)
            throw new IllegalArgumentException("UserDto cannot be null.");
        UserModel user = new UserModel();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setRole(dto.getRole());
        user.setPassword(dto.getPassword());
        user.setBirthDate(dto.getBirthDate());
        user.setRegisteredDate(dto.getRegisteredDate());
        user.setUpdateDate(dto.getUpdateDate());

//        CartDto cartDto = dto.getCart()==null ? new CartDto(): dto.getCart(); // null
//        CartModel cartEntity = CartConvertor.toCartEntity(cartDto);
//        user.setCart(cartEntity);

        return user;
    }
}
