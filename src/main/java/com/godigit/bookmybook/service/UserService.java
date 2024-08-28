package com.godigit.bookmybook.service;

import com.godigit.bookmybook.dto.DataHolder;
import com.godigit.bookmybook.dto.UserDTO;
import com.godigit.bookmybook.model.UserModel;
import com.godigit.bookmybook.repository.UserRepository;
import com.godigit.bookmybook.util.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenUtility tokenUtility;

    public UserDTO addUser(UserDTO userDTO) {
        UserModel userModel = userRepository.findByEmail(userDTO.getEmail());
        if (userModel != null) {
            throw new RuntimeException("User already exist.");
        }

        UserModel saveModal = new UserModel(userDTO);
        userRepository.save(saveModal);
        return new UserDTO(saveModal);
    }

    public List<UserDTO> getAllUser(String token) {
        DataHolder decode = tokenUtility.decode(token);

        if (!decode.getRole().equalsIgnoreCase("Admin"))
            throw new RuntimeException("Customer has no access the to retrieve all user details");

        List<UserModel> allUserData = userRepository.findAll();
        return allUserData.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        UserModel userModel = userRepository.findById(id).orElseThrow(() -> new RuntimeException("No such user Exist"));
        return new UserDTO(userModel);
    }


    public UserDTO updateUser(Long id, UserDTO userDTO) {
        UserModel userModel = userRepository.findById(id).orElseThrow();

        if (userDTO.getFirstname() != null)
            userModel.setFirstname(userDTO.getFirstname());
        if (userDTO.getLastname() != null)
            userModel.setFirstname(userDTO.getLastname());
        if (userDTO.getEmail() != null)
            userModel.setEmail(userDTO.getEmail());
        if(userDTO.getPassword() != null)
            userModel.setPassword(userDTO.getPassword());

        UserModel save = userRepository.save(userModel);
        return new UserDTO(save);
    }


    public String delete(Long id) {
        userRepository.deleteById(id);
        return "User details " + id + " Deleted ";
    }


    public String loginService(String email, String password) {
        System.out.println(email + " " + password);
        UserModel user = userRepository.findByEmail(email);

        if (!password.equals(user.getPassword())) {
            throw new IllegalArgumentException("Password don't match");
        }

//        token
        return tokenUtility.getToken(user.getId(), user.getRole());
    }

    public String deleteByToken(String token) {
        DataHolder decode = tokenUtility.decode(token);
        return delete(decode.getId());
    }

    public UserDTO updateByToken(String token, UserDTO userDTO) {
        DataHolder dataHolder = tokenUtility.decode(token);
        return updateUser(dataHolder.getId(), userDTO);
    }

    public UserDTO getIdByToken(String token, long id) {
        DataHolder decode = tokenUtility.decode(token);
        if (!decode.getRole().equalsIgnoreCase("ADmin")) {
            throw new RuntimeException("Customer can't see other customer");
        }

        return getUserById(id);
    }
}
