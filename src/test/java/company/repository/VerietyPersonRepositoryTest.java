package company.repository;

import company.entity.VerietyPerson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class VerietyPersonRepositoryTest {
    @Autowired
    private VerietyPersonRepository verietyPersonRepository;
    @Test
    public void testSaveVerietyPerson() {
        VerietyPerson verietyPerson = new VerietyPerson();
        verietyPerson.setVeriety("Инвестор");
        VerietyPerson savedVerietyPerson = verietyPersonRepository.save(verietyPerson);
        assertNotNull(savedVerietyPerson.getId());
    }
    @Test
    public void testFindById() {
        VerietyPerson verietyPerson = new VerietyPerson();
        verietyPerson.setVeriety("Инвестор");
        VerietyPerson savedVerietyPerson = verietyPersonRepository.save(verietyPerson);
        Long verietyPersonId = savedVerietyPerson.getId();
        VerietyPerson foundVerietyPerson = verietyPersonRepository.findById(verietyPersonId).orElse(null);
        assertNotNull(foundVerietyPerson);
        assertEquals(verietyPersonId, foundVerietyPerson.getId());
    }
    @Test
    public void testUpdateVerietyPerson() {
        VerietyPerson verietyPerson = new VerietyPerson();
        verietyPerson.setVeriety("Инвестор");
        VerietyPerson savedVerietyPerson = verietyPersonRepository.save(verietyPerson);
        Long verietyPersonId = savedVerietyPerson.getId();
        savedVerietyPerson.setVeriety("Доверительное");
        verietyPersonRepository.save(savedVerietyPerson);
        VerietyPerson updatedVerietyPerson = verietyPersonRepository.findById(verietyPersonId).orElse(null);
        assertNotNull(updatedVerietyPerson);
        assertEquals("Доверительное", updatedVerietyPerson.getVeriety());
    }
    @Test
    public void testDeleteVerietyPerson() {
        VerietyPerson verietyPerson = new VerietyPerson();
        verietyPerson.setVeriety("Инвестор");
        VerietyPerson savedVerietyPerson = verietyPersonRepository.save(verietyPerson);
        Long verietyPersonId = savedVerietyPerson.getId();
        verietyPersonRepository.deleteById(verietyPersonId);
        assertTrue(verietyPersonRepository.findById(verietyPersonId).isEmpty());
    }
}
