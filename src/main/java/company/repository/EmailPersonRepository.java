package company.repository;

import company.entity.EmailPerson;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailPersonRepository extends CrudRepository<EmailPerson, Long> {

    boolean existsByPersonId(Long id);
}
