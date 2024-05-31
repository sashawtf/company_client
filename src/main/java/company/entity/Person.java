package company.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Table
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "veriety_id")
    private Long verietyId;
    @Column(name = "status_id")
    private Long statusId;

    @NotBlank(message = "ИНН не должно быть пустым")
    @Pattern(regexp = "\\d{10}|\\d{12}", message = "ИНН должен содержать 10 или 12 цифр.")
    private String inn;

    @NotBlank(message = "Тип клиента не должно быть пустым")
    @Pattern(regexp = "^[A-Za-zА-Яа-я ]+$", message = "Может содержать только буквы.")
    private String type;

    @NotBlank(message = "Шифр клиента не должно быть пустым")
    private String shifer;

    @NotNull(message = "Дата регистрации клиента не должна быть пустой")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date data;
}
