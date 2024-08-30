package com.godigit.bookmybook.service.interfaceimp;

import com.godigit.bookmybook.dto.UserDTO;

import java.util.List;

public interface UserServiceInterface {
    UserDTO addUser(UserDTO dto);

    List<UserDTO> getAllUser();

    UserDTO getUserById(Long id);

    UserDTO updateUser(Long id, UserDTO userDTO);

    String delete(Long id);
}
