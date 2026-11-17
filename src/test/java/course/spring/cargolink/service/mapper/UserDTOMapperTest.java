package course.spring.cargolink.service.mapper;

import course.spring.cargolink.entity.user.User;
import course.spring.cargolink.entity.user.UserRole;
import course.spring.cargolink.dto.user.UserRegistrationDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserDTOMapperTest {
    private static final String EMAIL = "example@example.com";
    private static final String NAME = "example";
    private static final String PASSWORD = "example";
    private static final String WAREHOUSE_TOKEN = "example";
    private static final UserRole WAREHOUSE = UserRole.WAREHOUSE_MANAGER;
    private static final UserRole CUSTOMER = UserRole.CUSTOMER;
    private final UserDTOMapper userDTOMapper = new UserDTOMapper();

    @Test
    void testGetUserFromUserRegistrationDTONormalCase() {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO(EMAIL, PASSWORD, NAME, WAREHOUSE_TOKEN);
        User user = userDTOMapper.getUserFromUserRegistrationDTO(userRegistrationDTO, PASSWORD);
        assertEquals(EMAIL, user.getEmail());
        assertEquals(NAME, user.getName());
        assertEquals(PASSWORD, user.getPassword());
        assertEquals(WAREHOUSE_TOKEN, user.getWarehouse_token());
        assertEquals(WAREHOUSE, user.getRole());
    }
    @Test
    void testGetUserFromUserRegistrationDTONullWarehouseToken() {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO(EMAIL, PASSWORD, NAME, null);
        User user = userDTOMapper.getUserFromUserRegistrationDTO(userRegistrationDTO, PASSWORD);
        assertEquals(EMAIL, user.getEmail());
        assertEquals(NAME, user.getName());
        assertEquals(PASSWORD, user.getPassword());
        assertNull(user.getWarehouse_token());
        assertEquals(CUSTOMER, user.getRole());
    }
    @Test
    void testGetUserFromUserRegistrationDTOBlankWarehouseToken() {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO(EMAIL, PASSWORD, NAME, "   ");
        User user = userDTOMapper.getUserFromUserRegistrationDTO(userRegistrationDTO, PASSWORD);
        assertEquals(EMAIL, user.getEmail());
        assertEquals(NAME, user.getName());
        assertEquals(PASSWORD, user.getPassword());
        assertNull(user.getWarehouse_token());
        assertEquals(CUSTOMER, user.getRole());
    }
}