package company.repository;

import company.entity.EmailPerson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmailPersonRepositoryTest {

    @Autowired
    private EmailPersonRepository emailPersonRepository;

    @Test
    @DirtiesContext
    public void testExistsByPersonId() {
        EmailPerson emailPerson = new EmailPerson();
        emailPerson.setPersonId(1L);
        emailPerson.setEmail("test@example.com");
        emailPersonRepository.save(emailPerson);
        assertTrue(emailPersonRepository.existsByPersonId(1L));
        assertFalse(emailPersonRepository.existsByPersonId(999L));
    }
}
