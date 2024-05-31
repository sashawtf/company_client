package company.repository;

import company.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    @DirtiesContext
    public void testExistsByStatusId() {
        Person person = new Person();
        person.setVerietyId(1L);
        person.setStatusId(1L);
        person.setInn("1234567890");
        person.setType("Физическое лицо");
        person.setShifer("CL001");
        person.setData(new Date());
        personRepository.save(person);

        assertTrue(personRepository.existsByStatusId(1L));

        assertFalse(personRepository.existsByStatusId(999L));
    }

    @Test
    @DirtiesContext
    public void testExistsByVerietyId() {
        Person person = new Person();
        person.setVerietyId(1L);
        person.setStatusId(1L);
        person.setInn("1234567890");
        person.setType("Физическое лицо");
        person.setShifer("CL001");
        person.setData(new Date());
        personRepository.save(person);

        assertTrue(personRepository.existsByVerietyId(1L));

        assertFalse(personRepository.existsByVerietyId(999L));
    }
}