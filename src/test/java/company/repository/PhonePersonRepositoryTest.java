package company.repository;

import company.entity.PhonePerson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PhonePersonRepositoryTest {

    @Autowired
    private PhonePersonRepository phonePersonRepository;

    @Test
    @DirtiesContext
    public void testExistsByPersonId() {
        PhonePerson phonePerson = new PhonePerson();
        phonePerson.setPersonId(1L);
        phonePerson.setPhone("+12345678908");
        phonePersonRepository.save(phonePerson);

        assertTrue(phonePersonRepository.existsByPersonId(1L));

        assertFalse(phonePersonRepository.existsByPersonId(999L));
    }
}