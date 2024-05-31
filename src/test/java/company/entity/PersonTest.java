package company.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidPerson() {
        Person person = new Person();
        person.setVerietyId(1L);
        person.setStatusId(1L);
        person.setInn("1234567890");
        person.setType("Физическое лицо");
        person.setShifer("CL001");
        person.setData(new Date());

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidEmptyInn() {
        Person person = new Person();
        person.setVerietyId(1L);
        person.setStatusId(1L);
        person.setInn("");
        person.setType("Физическое лицо");
        person.setShifer("CL001");
        person.setData(new Date());

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertFalse(violations.isEmpty(), "Expected violations for empty INN");
    }

    @Test
    public void testInvalidEmptyType() {
        Person person = new Person();
        person.setVerietyId(1L);
        person.setStatusId(1L);
        person.setInn("1234567890");
        person.setType("");
        person.setShifer("CL001");
        person.setData(new Date());

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertFalse(violations.isEmpty(), "Expected violations for empty Type");
    }
}