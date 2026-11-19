package course.spring.cargolink.entity.warehouse;

import course.spring.cargolink.entity.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    @Getter @Setter
    private String name;

    @NotBlank
    @Column(nullable = false, unique = true)
    @Getter @Setter
    private String regionCode;

    @NotBlank
    @Column(nullable = false)
    @Getter @Setter
    private String address;

    @Min(1)
    @Column(nullable = false)
    @Getter @Setter
    private int capacity;

    @OneToOne
    @JoinColumn(name = "manager_id", unique = true)
    private User manager;

    @OneToMany(mappedBy = "warehouse")
    private List<InventoryItem> inventoryItems = new ArrayList<>();
}
