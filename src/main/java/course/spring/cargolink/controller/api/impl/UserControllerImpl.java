package course.spring.cargolink.controller.api.impl;

import course.spring.cargolink.controller.api.UserController;
import course.spring.cargolink.dto.user.UserCredentialsDTO;
import course.spring.cargolink.dto.user.UserDataDTO;
import course.spring.cargolink.dto.user.UserRegistrationDTO;
import course.spring.cargolink.exception.EmailAlreadyInUseException;
import course.spring.cargolink.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Autowired
    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public void register(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) throws EmailAlreadyInUseException {
        userService.registerUser(userRegistrationDTO);
    }

    @PostMapping("/login")
    @Override
    public ResponseEntity<UserDataDTO> login(@Valid @RequestBody UserCredentialsDTO userCredentialsDTO) {
        if (!userService.validateUserCredentials(userCredentialsDTO)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<UserDataDTO> user = userService.getUserByEmail(userCredentialsDTO.email());
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
