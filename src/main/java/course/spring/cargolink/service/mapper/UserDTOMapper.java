package course.spring.cargolink.service.mapper;

import course.spring.cargolink.entity.user.User;
import course.spring.cargolink.entity.user.UserRole;
import course.spring.dto.UserDataDTO;
import course.spring.dto.UserRegistrationDTO;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {

    public User getUserFromUserRegistrationDTO(UserRegistrationDTO userRegistrationDTO, String hashedPassword) {
        UserRole userRole = UserRole.CUSTOMER;
        if (userRegistrationDTO.warehouseToken() != null && !userRegistrationDTO.warehouseToken().isBlank()) {
            userRole = UserRole.WAREHOUSE_MANAGER;
        }
        return User.builder()
                .email(userRegistrationDTO.email())
                .name(userRegistrationDTO.name())
                .password(hashedPassword)
                .warehouse_token(userRegistrationDTO.warehouseToken())
                .role(userRole)
                .build();
    }

    public UserDataDTO getUserDataDTOFromUser(User user) {
        return new UserDataDTO(user.getName(), user.getWarehouse_token(), user.getRole(), user.getId());
    }
}