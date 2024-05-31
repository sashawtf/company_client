package company.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table
public class PhonePerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "person_id")
    private Long personId;
    @NotBlank(message = "Телефон не должен быть пустым")
    @Pattern(regexp = "\\+?[0-9]{10,15}", message = "Телефон должен быть в международном формате")
    private String phone;
}
