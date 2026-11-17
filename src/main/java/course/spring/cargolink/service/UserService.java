package course.spring.cargolink.service;

import course.spring.cargolink.exception.EmailAlreadyInUseException;
import course.spring.cargolink.dto.user.UserCredentialsDTO;
import course.spring.cargolink.dto.user.UserDataDTO;
import course.spring.cargolink.dto.user.UserRegistrationDTO;

import java.util.Optional;

public interface UserService {
    Optional<UserDataDTO> getUserByEmail(String email);
    void registerUser(UserRegistrationDTO user) throws EmailAlreadyInUseException;
    boolean validateUserCredentials(UserCredentialsDTO userCredentials);
    void deleteUser(long id);
}
