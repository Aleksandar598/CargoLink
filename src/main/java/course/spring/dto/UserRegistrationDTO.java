package course.spring.dto;

public record UserRegistrationDTO(String email, String password, String name, String warehouseToken) {
    public UserRegistrationDTO {
        if (email == null) {
            throw new IllegalArgumentException("Email is required");
        }
        if (password == null) {
            throw new IllegalArgumentException("Password is required");
        }
        if (name == null) {
            throw new IllegalArgumentException("Name is required");
        }
    }
}
