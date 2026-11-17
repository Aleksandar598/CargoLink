package course.spring.cargolink.controller.user.impl;

import course.spring.cargolink.controller.api.impl.UserControllerImpl;
import course.spring.cargolink.dto.user.UserCredentialsDTO;
import course.spring.cargolink.dto.user.UserDataDTO;
import course.spring.cargolink.dto.user.UserRegistrationDTO;
import course.spring.cargolink.entity.user.UserRole;
import course.spring.cargolink.exception.EmailAlreadyInUseException;
import course.spring.cargolink.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerImplTest {

    private static final String EMAIL = "alex@example.com";
    private static final String PASSWORD = "plain-password";

    @Mock
    private UserService userService;

    private UserControllerImpl userController;

    @BeforeEach
    void setUp() {
        userController = new UserControllerImpl(userService);
    }

    @Test
    void register_delegatesRegistrationToUserService() throws EmailAlreadyInUseException {
        UserRegistrationDTO registration = new UserRegistrationDTO(
                EMAIL, PASSWORD, "Aleksandar", null);

        userController.register(registration);

        verify(userService).registerUser(registration);
    }

    @Test
    void register_propagatesException_whenEmailIsAlreadyUsed() throws EmailAlreadyInUseException {
        UserRegistrationDTO registration = new UserRegistrationDTO(
                EMAIL, PASSWORD, "Aleksandar", null);
        EmailAlreadyInUseException exception = new EmailAlreadyInUseException("Email Already In Use");
        org.mockito.Mockito.doThrow(exception).when(userService).registerUser(registration);

        EmailAlreadyInUseException thrown = assertThrows(
                EmailAlreadyInUseException.class,
                () -> userController.register(registration));

        assertEquals("Email Already In Use", thrown.getMessage());
    }

    @Test
    void login_returnsUnauthorized_whenCredentialsAreInvalid() {
        UserCredentialsDTO credentials = new UserCredentialsDTO(EMAIL, PASSWORD);
        when(userService.validateUserCredentials(credentials)).thenReturn(false);

        ResponseEntity<UserDataDTO> response = userController.login(credentials);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
        verify(userService, never()).getUserByEmail(EMAIL);
    }

    @Test
    void login_returnsUserData_whenCredentialsAreValidAndUserExists() {
        UserCredentialsDTO credentials = new UserCredentialsDTO(EMAIL, PASSWORD);
        UserDataDTO userData = new UserDataDTO("Aleksandar", null, UserRole.CUSTOMER, 1L);
        when(userService.validateUserCredentials(credentials)).thenReturn(true);
        when(userService.getUserByEmail(EMAIL)).thenReturn(Optional.of(userData));

        ResponseEntity<UserDataDTO> response = userController.login(credentials);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userData, response.getBody());
        verify(userService).getUserByEmail(EMAIL);
    }

    @Test
    void login_returnsNotFound_whenCredentialsAreValidButUserNoLongerExists() {
        UserCredentialsDTO credentials = new UserCredentialsDTO(EMAIL, PASSWORD);
        when(userService.validateUserCredentials(credentials)).thenReturn(true);
        when(userService.getUserByEmail(EMAIL)).thenReturn(Optional.empty());

        ResponseEntity<UserDataDTO> response = userController.login(credentials);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}
