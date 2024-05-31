package company.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
class VerietyPersonTest {
    private Validator validator;
    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @Test
    public void testValidVerietyPerson() {
        VerietyPerson verietyPerson = new VerietyPerson();
        verietyPerson.setVeriety("Investor");
        Set<ConstraintViolation<VerietyPerson>> violations = validator.validate(verietyPerson);
        assertEquals(0, violations.size());
    }
    @Test
    public void testInvalidEmptyVeriety() {
        VerietyPerson verietyPerson = new VerietyPerson();
        verietyPerson.setVeriety("");
        Set<ConstraintViolation<VerietyPerson>> violations = validator.validate(verietyPerson);
        assertFalse(violations.isEmpty(), "Expected violations for empty veriety");
    }
    @Test
    public void testInvalidVerietyFormat() {
        VerietyPerson verietyPerson = new VerietyPerson();
        verietyPerson.setVeriety("Investor123");
        Set<ConstraintViolation<VerietyPerson>> violations = validator.validate(verietyPerson);
        assertFalse(violations.isEmpty(), "Expected violations for invalid veriety format");
    }
}