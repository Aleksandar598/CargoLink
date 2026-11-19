package course.spring.cargolink.entity.user;

import lombok.Getter;

@Getter
public enum UserRole {
    CUSTOMER("ROLE_CUSTOMER"),
    WAREHOUSE_MANAGER("ROLE_WAREHOUSE_MANAGER"),
    ADMINISTRATOR("ROLE_ADMINISTRATOR");

    private final String authority;

    UserRole(String authority) {
        this.authority = authority;
    }

}