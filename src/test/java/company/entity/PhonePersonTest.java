package company.entity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PhonePersonTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidPhonePerson() {
        PhonePerson phonePerson = new PhonePerson();
        phonePerson.setPersonId(1L);
        phonePerson.setPhone("+12345678901");

        Set<ConstraintViolation<PhonePerson>> violations = validator.validate(phonePerson);
        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidEmptyPhone() {
        PhonePerson phonePerson = new PhonePerson();
        phonePerson.setPersonId(1L);
        phonePerson.setPhone("");

        Set<ConstraintViolation<PhonePerson>> violations = validator.validate(phonePerson);
        assertFalse(violations.isEmpty(), "Expected violations for empty phone");
    }

    @Test
    public void testInvalidPhoneFormat() {
        PhonePerson phonePerson = new PhonePerson();
        phonePerson.setPersonId(1L);
        phonePerson.setPhone("123456789");

        Set<ConstraintViolation<PhonePerson>> violations = validator.validate(phonePerson);
        assertFalse(violations.isEmpty(), "Expected violations for invalid phone format");
    }
}
