package company.entity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class EmailPersonTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidEmailPerson() {
        EmailPerson emailPerson = new EmailPerson();
        emailPerson.setPersonId(1L);
        emailPerson.setEmail("example@example.com");

        Set<ConstraintViolation<EmailPerson>> violations = validator.validate(emailPerson);
        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidEmptyEmail() {
        EmailPerson emailPerson = new EmailPerson();
        emailPerson.setPersonId(1L);
        emailPerson.setEmail("");

        Set<ConstraintViolation<EmailPerson>> violations = validator.validate(emailPerson);
        assertFalse(violations.isEmpty(), "Expected violations for empty email");
    }

    @Test
    public void testInvalidEmailFormat() {
        EmailPerson emailPerson = new EmailPerson();
        emailPerson.setPersonId(1L);
        emailPerson.setEmail("invalidemail");

        Set<ConstraintViolation<EmailPerson>> violations = validator.validate(emailPerson);
        assertFalse(violations.isEmpty(), "Expected violations for invalid email format");
    }
}