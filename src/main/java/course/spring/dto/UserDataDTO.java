package course.spring.dto;

import course.spring.cargolink.entity.user.UserRole;

public record UserDataDTO(String name, String warehouse_token, UserRole role, long id) {

    public UserDataDTO {
        if (name == null) {
            throw new IllegalArgumentException("Name is required");
        }
        if (role == null) {
            throw new IllegalArgumentException("Role is required");
        }
    }
}
