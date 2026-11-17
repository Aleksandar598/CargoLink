package course.spring.cargolink.service.impl;

import course.spring.cargolink.entity.user.User;
import course.spring.cargolink.exception.EmailAlreadyInUseException;
import course.spring.cargolink.exception.UserNotFoundException;
import course.spring.cargolink.repositiory.UserRepository;
import course.spring.cargolink.service.UserService;
import course.spring.cargolink.service.mapper.UserDTOMapper;
import course.spring.cargolink.dto.user.UserCredentialsDTO;
import course.spring.cargolink.dto.user.UserDataDTO;
import course.spring.cargolink.dto.user.UserRegistrationDTO;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@NoArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserDTOMapper userDTOMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository, UserDTOMapper userDTOMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Optional<UserDataDTO> getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(value -> userDTOMapper.getUserDataDTOFromUser(value));
    }

    @Override
    public void registerUser(UserRegistrationDTO registrationDTO) throws EmailAlreadyInUseException {
        if (userRepository.findByEmail(registrationDTO.email()).isPresent()) {
            throw new EmailAlreadyInUseException("Email Already In Use");
        }
        String password = passwordEncoder.encode(registrationDTO.password());
        User user = userDTOMapper.getUserFromUserRegistrationDTO(registrationDTO, password);
        userRepository.save(user);
    }

    @Override
    public boolean validateUserCredentials(UserCredentialsDTO userCredentials) {
        Optional<User> user = userRepository.findByEmail(userCredentials.email());
        return user.filter(value -> passwordEncoder.matches(userCredentials.password(),
                value.getPassword())).isPresent();
    }

    @Override
    public void deleteUser(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return;
        }
        throw new UserNotFoundException("User with id " + id + " not found");
    }
}
