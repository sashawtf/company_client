package company.repository;

import company.entity.StatusPerson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StatusPersonRepositoryTest {

    @Autowired
    private StatusPersonRepository statusPersonRepository;

    @Test
    public void testSaveStatusPerson() {
        StatusPerson statusPerson = new StatusPerson();
        statusPerson.setStatus("Active");

        StatusPerson savedStatusPerson = statusPersonRepository.save(statusPerson);
        assertNotNull(savedStatusPerson.getId());
    }

    @Test
    public void testFindById() {
        StatusPerson statusPerson = new StatusPerson();
        statusPerson.setStatus("Active");

        StatusPerson savedStatusPerson = statusPersonRepository.save(statusPerson);
        Long statusPersonId = savedStatusPerson.getId();

        StatusPerson foundStatusPerson = statusPersonRepository.findById(statusPersonId).orElse(null);

        assertNotNull(foundStatusPerson);
        assertEquals(statusPersonId, foundStatusPerson.getId());
    }

    @Test
    public void testUpdateStatusPerson() {
        StatusPerson statusPerson = new StatusPerson();
        statusPerson.setStatus("Active");

        StatusPerson savedStatusPerson = statusPersonRepository.save(statusPerson);
        Long statusPersonId = savedStatusPerson.getId();

        savedStatusPerson.setStatus("Inactive");
        statusPersonRepository.save(savedStatusPerson);

        StatusPerson updatedStatusPerson = statusPersonRepository.findById(statusPersonId).orElse(null);

        assertNotNull(updatedStatusPerson);
        assertEquals("Inactive", updatedStatusPerson.getStatus());
    }

    @Test
    public void testDeleteStatusPerson() {
        StatusPerson statusPerson = new StatusPerson();
        statusPerson.setStatus("Active");

        StatusPerson savedStatusPerson = statusPersonRepository.save(statusPerson);
        Long statusPersonId = savedStatusPerson.getId();

        statusPersonRepository.deleteById(statusPersonId);

        assertTrue(statusPersonRepository.findById(statusPersonId).isEmpty());
    }
}
