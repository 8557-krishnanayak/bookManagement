package com.godigit.bookmybook.converstion;


import com.godigit.bookmybook.dto.UserDTO;
import com.godigit.bookmybook.dto.WishListDTO;
import com.godigit.bookmybook.model.UserModel;
import com.godigit.bookmybook.model.WishListModel;

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

        WishListModel wishList = user.getWishList();
        WishListDTO wishDto = WishListConvertor.toDTO(wishList);
        wishDto.setUserId(user.getId());

        dto.setWishList(wishDto);
        return dto;
    }

    public static UserModel toEntity(UserDTO dto) {
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

        WishListDTO wishList = dto.getWishList();
        WishListModel entity = WishListConvertor.toEntity(wishList);
        entity.setUserId(dto.getId());

        user.setWishList(entity);
        System.out.println(user);
        return user;
    }
}
