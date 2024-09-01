package com.godigit.bookmybook.converstion;

import com.godigit.bookmybook.dto.AddressDTO;
import com.godigit.bookmybook.dto.BookDTO;
import com.godigit.bookmybook.dto.OrderDTO;
import com.godigit.bookmybook.dto.UserDTO;
import com.godigit.bookmybook.model.Address;
import com.godigit.bookmybook.model.BookModel;
import com.godigit.bookmybook.model.OrderModel;
import com.godigit.bookmybook.model.UserModel;

import java.util.List;
import java.util.stream.Collectors;

public class OrderConvertor {

    public static OrderDTO toDTO(OrderModel order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setPrice(order.getPrice());
        dto.setQuantity(order.getQuantity());

        Address address = order.getAddress();
        dto.setAddress(toAddressDto(address));

        BookDTO book_list = BookConvertor.toDTO(order.getBook());
        dto.setBook(book_list);

        return dto;
    }

    public static OrderModel toEntity(OrderDTO dto) {
        OrderModel order = new OrderModel();
        order.setId(dto.getId());
        order.setPrice(dto.getPrice());
        order.setQuantity(dto.getQuantity());

        AddressDTO address = dto.getAddress();
        order.setAddress(toAddressEntity(address));

        BookModel book_list = BookConvertor.toEntity(dto.getBook());
        order.setBook(book_list);

        return order;
    }

    public static Address toAddressEntity(AddressDTO dto) {
        return new Address(dto);
    }

    public static AddressDTO toAddressDto(Address address) {

        return AddressDTO.builder()
                .id(address.getId())
                .state(address.getState())
                .city(address.getCity())
                .type(address.getType())
                .pinCode(address.getPinCode())
                .build();
    }
}
