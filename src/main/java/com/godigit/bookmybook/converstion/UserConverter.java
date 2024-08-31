package com.godigit.bookmybook.converstion;


import com.godigit.bookmybook.dto.*;
import com.godigit.bookmybook.model.*;
import org.hibernate.query.Order;

import java.util.List;
import java.util.stream.Collectors;

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


        List<WishListDTO> wishList = user.getWishList().stream().map(WishListConvertor::toDTO).toList();
        dto.setWishList(wishList);


        List<OrderDTO> order = user.getOrders().stream().map(OrderConvertor::toDTO).toList();
        dto.setOrders(order);

        List<FeedBackDTO> feedback = user.getFeedbacks().stream().map(FeedbackConverter::toDTO).toList();
        dto.setFeedbacks(feedback);

        return dto;
    }

    public static UserModel toEntity(UserDTO dto) {

        if (dto == null)
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


        List<WishListModel> wishList = dto.getWishList().stream().map(WishListConvertor::toEntity).toList();
        user.setWishList(wishList);


        List<FeedBackModel> feedback = dto.getFeedbacks().stream().map(FeedbackConverter::toEntity).toList();
        user.setFeedbacks(feedback);


        List<OrderModel> order = dto.getOrders().stream().map(OrderConvertor::toEntity).toList();
        user.setOrders(order);

        System.out.println(user);
        return user;
    }
}
