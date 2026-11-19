package course.spring.cargolink.controller.api;

import course.spring.cargolink.dto.user.UserCredentialsDTO;
import course.spring.cargolink.dto.user.UserDataDTO;
import course.spring.cargolink.dto.user.UserRegistrationDTO;
import course.spring.cargolink.exception.EmailAlreadyInUseException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserController {

    void register(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) throws EmailAlreadyInUseException;

    ResponseEntity<UserDataDTO> login(@Valid @RequestBody UserCredentialsDTO userCredentialsDTO);
}
