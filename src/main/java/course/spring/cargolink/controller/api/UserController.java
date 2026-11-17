package course.spring.cargolink.controller.api;

import course.spring.cargolink.dto.user.UserRegistrationDTO;
import course.spring.cargolink.exception.EmailAlreadyInUseException;

public interface UserController {

    void register(UserRegistrationDTO userRegistrationDTO) throws EmailAlreadyInUseException;

}
