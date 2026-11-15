package course.spring.cargolink.service.user;

import course.spring.cargolink.entity.user.User;
import course.spring.cargolink.exception.EmailAlreadyInUseException;
import course.spring.dto.UserCredentialsDTO;
import course.spring.dto.UserDataDTO;
import course.spring.dto.UserRegistrationDTO;

import java.util.Optional;

public interface UserService {
    Optional<UserDataDTO> getUserByEmail(String email);
    void registerUser(UserRegistrationDTO user) throws EmailAlreadyInUseException;
    boolean validateUserCredentials(UserCredentialsDTO userCredentials);
    void deleteUser(long id);
}
