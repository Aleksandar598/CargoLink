package course.spring.cargolink.service.user.impl;

import course.spring.cargolink.entity.user.User;
import course.spring.cargolink.entity.user.UserRole;
import course.spring.cargolink.exception.EmailAlreadyInUseException;
import course.spring.cargolink.exception.UserNotFoundException;
import course.spring.cargolink.repositiory.UserRepository;
import course.spring.cargolink.service.impl.UserServiceImpl;
import course.spring.cargolink.service.mapper.UserDTOMapper;
import course.spring.cargolink.dto.user.UserCredentialsDTO;
import course.spring.cargolink.dto.user.UserDataDTO;
import course.spring.cargolink.dto.user.UserRegistrationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static final String EMAIL = "alex@example.com";
    private static final String PASSWORD = "plain-password";
    private static final String HASHED_PASSWORD = "hashed-password";

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDTOMapper userDTOMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl();
        userService.setUserRepository(userRepository, userDTOMapper, passwordEncoder);
    }

    @Test
    void getUserByEmail_returnsMappedDto_whenUserExists() {
        User user = user(1L);
        UserDataDTO expectedDto = new UserDataDTO("Aleksandar", null, UserRole.CUSTOMER, 1L);
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(user));
        when(userDTOMapper.getUserDataDTOFromUser(user)).thenReturn(expectedDto);

        Optional<UserDataDTO> result = userService.getUserByEmail(EMAIL);

        assertTrue(result.isPresent());
        assertEquals(expectedDto, result.get());
        verify(userRepository).findByEmail(EMAIL);
        verify(userDTOMapper).getUserDataDTOFromUser(user);
    }

    @Test
    void getUserByEmail_returnsEmpty_whenUserDoesNotExist() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());

        Optional<UserDataDTO> result = userService.getUserByEmail(EMAIL);

        assertTrue(result.isEmpty());
        verify(userRepository).findByEmail(EMAIL);
        verifyNoInteractions(userDTOMapper);
    }

    @Test
    void registerUser_encodesPasswordMapsAndSavesNewUser() throws EmailAlreadyInUseException {
        UserRegistrationDTO registration = new UserRegistrationDTO(EMAIL, PASSWORD, "Aleksandar", null);
        User mappedUser = user(1L);
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(PASSWORD)).thenReturn(HASHED_PASSWORD);
        when(userDTOMapper.getUserFromUserRegistrationDTO(registration, HASHED_PASSWORD))
                .thenReturn(mappedUser);

        userService.registerUser(registration);

        verify(passwordEncoder).encode(PASSWORD);
        verify(userDTOMapper).getUserFromUserRegistrationDTO(registration, HASHED_PASSWORD);
        verify(userRepository).save(mappedUser);
    }

    @Test
    void registerUser_throwsExceptionAndDoesNotSave_whenEmailExists() {
        UserRegistrationDTO registration = new UserRegistrationDTO(EMAIL, PASSWORD, "Aleksandar", null);
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(user(1L)));

        assertThrows(EmailAlreadyInUseException.class, () -> userService.registerUser(registration));

        verifyNoInteractions(passwordEncoder, userDTOMapper);
        verify(userRepository, never()).save(org.mockito.ArgumentMatchers.any(User.class));
    }

    @Test
    void validateUserCredentials_returnsTrue_whenCredentialsAreCorrect() {
        UserCredentialsDTO credentials = new UserCredentialsDTO(EMAIL, PASSWORD);
        User user = user(1L);
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(PASSWORD, HASHED_PASSWORD)).thenReturn(true);

        boolean result = userService.validateUserCredentials(credentials);

        assertTrue(result);
        verify(passwordEncoder).matches(PASSWORD, HASHED_PASSWORD);
    }

    @Test
    void validateUserCredentials_returnsFalse_whenUserDoesNotExist() {
        UserCredentialsDTO credentials = new UserCredentialsDTO(EMAIL, PASSWORD);
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());

        boolean result = userService.validateUserCredentials(credentials);

        assertFalse(result);
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void validateUserCredentials_returnsFalse_whenPasswordDoesNotMatch() {
        UserCredentialsDTO credentials = new UserCredentialsDTO(EMAIL, PASSWORD);
        User user = user(1L);
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(PASSWORD, HASHED_PASSWORD)).thenReturn(false);

        boolean result = userService.validateUserCredentials(credentials);

        assertFalse(result);
        verify(passwordEncoder).matches(PASSWORD, HASHED_PASSWORD);
    }

    @Test
    void deleteUser_deletesUser_whenIdExists() {
        User user = user(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository).delete(user);
    }

    @Test
    void deleteUser_throwsException_whenIdDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));

        verify(userRepository, never()).delete(org.mockito.ArgumentMatchers.any(User.class));
    }

    private User user(Long id) {
        return User.builder()
                .id(id)
                .email(EMAIL)
                .password(HASHED_PASSWORD)
                .name("Aleksandar")
                .role(UserRole.CUSTOMER)
                .build();
    }

}
