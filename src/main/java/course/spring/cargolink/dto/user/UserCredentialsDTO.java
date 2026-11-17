package course.spring.cargolink.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCredentialsDTO(
        @Email @NotBlank String email,
        @NotBlank @Size(min =8)
        String password) {
}
