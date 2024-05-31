package company.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
class StatusPersonTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidStatusPerson() {
        StatusPerson statusPerson = new StatusPerson();
        statusPerson.setStatus("Active");

        Set<ConstraintViolation<StatusPerson>> violations = validator.validate(statusPerson);
        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidEmptyStatus() {
        StatusPerson statusPerson = new StatusPerson();
        statusPerson.setStatus("");

        Set<ConstraintViolation<StatusPerson>> violations = validator.validate(statusPerson);
        assertFalse(violations.isEmpty(), "Expected violations for empty status");
    }

    @Test
    public void testInvalidStatusFormat() {
        StatusPerson statusPerson = new StatusPerson();
        statusPerson.setStatus("Active123");

        Set<ConstraintViolation<StatusPerson>> violations = validator.validate(statusPerson);
        assertFalse(violations.isEmpty(), "Expected violations for invalid status format");
    }
}