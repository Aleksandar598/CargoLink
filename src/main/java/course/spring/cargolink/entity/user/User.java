package course.spring.cargolink.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email
    @NotBlank
    @Column(unique = true,  nullable = false)
    @NonNull
    @Getter @Setter
    private String email;
    @NotBlank
    @Column(nullable = false)
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @NonNull
    private String password;
    @NotBlank
    @Column(nullable = false)
    @NonNull
    @Getter @Setter
    private String name;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NonNull
    @Getter @Setter
    private UserRole role;
    @Getter @Setter
    private String warehouse_token;
}
