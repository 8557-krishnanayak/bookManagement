package com.godigit.bookmybook.service;

import com.godigit.bookmybook.converstion.UserConverter;
import com.godigit.bookmybook.dto.DataHolder;
import com.godigit.bookmybook.dto.UserDTO;
import com.godigit.bookmybook.exception.ForbiddenException;
import com.godigit.bookmybook.exception.PasswordMissMatchException;
import com.godigit.bookmybook.exception.ResourceAlreadyExistException;
import com.godigit.bookmybook.exception.ResourceNotFoundException;
import com.godigit.bookmybook.model.UserModel;
import com.godigit.bookmybook.repository.UserRepository;
import com.godigit.bookmybook.service.interfaceimp.UserServiceInterface;
import com.godigit.bookmybook.util.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenUtility tokenUtility;


    /**
     * Adds a new user to the system.
     * This method first checks if a user with the provided email already exists.
     * If the user is found, a ResourceAlreadyExistException is thrown.
     * If the user does not exist, the user data is converted from a DTO to an entity model
     * and saved to the repository. The saved entity is then converted back to a DTO and returned.
     *
     * @param userDTO the data transfer object containing user details
     * @return the saved user as a data transfer object
     * @throws ResourceAlreadyExistException if a user with the provided email already exists
     */
    @Override
    public UserDTO addUser(UserDTO userDTO) {
        String email = userDTO.getEmail();
        UserModel userModel = userRepository.findByEmail(email).orElse(null);

        if (userModel != null)
            throw new ResourceAlreadyExistException("user already exist with the given email id: " + email);

        UserModel saveModal = UserConverter.toEntity(userDTO);
        System.out.println(saveModal);
        UserModel save = userRepository.save(saveModal);

        return UserConverter.toDTO(save);
    }

    /**
     * Retrieves all users from the system.
     * This method fetches all user data from the repository, converts each user entity
     * to a DTO, and returns a list of these DTOs.
     *
     * @return a list of all users as data transfer objects ,i.e, in the form UserDTO
     */
    @Override
    public List<UserDTO> getAllUser() {
        List<UserModel> allUserData = userRepository.findAll();
        return allUserData.stream().map(UserConverter::toDTO).collect(Collectors.toList());
    }


    /**
     * Retrieves a user by their ID.
     * This method search for a user in the repository by their ID. If the user is not found,
     * a ResourceNotFoundException is thrown. If the user is found, data is converted
     * from an entity model to a DTO and returned.
     *
     * @param id the ID of the user to retrieve
     * @return the user as a data transfer object
     * @throws ResourceNotFoundException if no user with the provided ID is found
     */
    @Override
    public UserDTO getUserById(Long id) {
        UserModel userModel = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No such user exist of user id: " + id));
        return UserConverter.toDTO(userModel);
    }


    /**
     * Updates an existing user's details.
     * This method retrieves a user by their ID from the repository. If the user is not found,
     * a ResourceNotFoundException is thrown. The method then updates the user's details with
     * the provided data from the UserDTO, if the corresponding fields are not null.
     * Finally, the updated user entity is saved back to the repository and converted to a DTO.
     *
     * @param id      the ID of the user to update
     * @param userDTO the data transfer object containing updated user details
     * @return the updated user as a data transfer object
     * @throws ResourceNotFoundException if no user with the provided ID is found
     */
    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        UserModel userModel = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No such user exist of user id: " + id));

        if (userDTO.getFirstname() != null)
            userModel.setFirstname(userDTO.getFirstname());
        if (userDTO.getLastname() != null)
            userModel.setFirstname(userDTO.getLastname());
        if (userDTO.getEmail() != null)
            userModel.setEmail(userDTO.getEmail());
        if (userDTO.getPassword() != null)
            userModel.setPassword(userDTO.getPassword());

        UserModel save = userRepository.save(userModel);
        return UserConverter.toDTO(save);
    }


    /**
     * Deletes a user by their ID.
     * This method removes a user from the repository based on the provided ID.
     * After deletion, it returns a confirmation message indicating the user ID that was deleted.
     *
     * @param id the ID of the user to delete
     * @return a confirmation message indicating the user ID that was deleted
     */
    @Override
    public String delete(Long id) {
        userRepository.deleteById(id);
        return "User details " + id + " Deleted ";
    }

    /**
     * Authenticates a user based on their email and password.
     * This method searches for a user in the repository by their email. If the user is not found,
     * a ResourceNotFoundException is thrown. If the user is found, the provided password is compared
     * with the stored password. If the passwords do not match, a PasswordMissMatchException is thrown.
     * If authentication is successful, a token is generated and returned.
     *
     * @param email    the email of the user attempting to log in
     * @param password the password of the user attempting to log in
     * @return a token for the authenticated user
     * @throws ResourceNotFoundException  if no user with the provided email is found
     * @throws PasswordMissMatchException if the provided password does not match the stored password
     */
    public String loginService(String email, String password) {
        UserModel user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("No such user with email: " + email));

        if (!password.equals(user.getPassword())) {
            throw new PasswordMissMatchException();
        }

        return tokenUtility.getToken(user.getId(), user.getRole()); // token
    }


    /**
     * Deletes a user based on the provided token.
     * This method decodes the token to retrieve the user ID and then deletes the user
     * with that ID from the repository. It returns a confirmation message indicating
     * the user ID that was deleted.
     *
     * @param token the token containing user information
     * @return a confirmation message indicating the user ID that was deleted
     */
    public String deleteByToken(String token) {
        DataHolder decode = tokenUtility.decode(token);
        return delete(decode.getId());
    }

    /**
     * Retrieves all users if the requester has admin role.
     * This method decodes the token to retrieve the user's role. If the role is not "Admin",
     * a ForbiddenException is thrown. If the role is "Admin", it retrieves and returns all users
     * as data transfer objects (DTOs).
     *
     * @param token the token containing user information
     * @return a list of all users as DTO
     * @throws ForbiddenException if the user does not have admin privileges
     */
    public List<UserDTO> getAllUser(String token) {
        DataHolder decode = tokenUtility.decode(token);

        if (!decode.getRole().equalsIgnoreCase("Admin"))
            throw new ForbiddenException("Customer has no access the to retrieve all user details");
        return getAllUser();
    }

    /**
     * Updates a user's details based on the provided token.
     * This method decodes the token to retrieve the user ID and then updates the user's details
     * with the provided data from the UserDTO. The updated user entity is saved back to the repository
     * and converted to a DTO.
     *
     * @param token   the token containing user information
     * @param userDTO the data transfer object containing updated user details
     * @return the updated user as a DTO
     */
    public UserDTO updateByToken(String token, UserDTO userDTO) {
        DataHolder dataHolder = tokenUtility.decode(token);
        return updateUser(dataHolder.getId(), userDTO);
    }

    /**
     * Retrieves a user by their ID if the requester has admin privileges.
     * This method decodes the token to retrieve the user's role. If the role is not "Admin",
     * a ForbiddenException is thrown. If the role is "Admin", it retrieves and returns the user
     * with the specified ID as a data transfer object (DTO).
     *
     * @param token the token containing user information
     * @param id    the ID of the user to retrieve
     * @return the user as a DTO
     * @throws ForbiddenException if the user does not have admin privileges
     */
    public UserDTO getIdByToken(String token, long id) {
        DataHolder decode = tokenUtility.decode(token);
        if (!decode.getRole().equalsIgnoreCase("Admin")) {
            throw new ForbiddenException();
        }

        return getUserById(id);
    }
}
