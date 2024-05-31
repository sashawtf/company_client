package company.repository;

import company.entity.PhonePerson;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhonePersonRepository extends CrudRepository<PhonePerson, Long> {

    boolean existsByPersonId(Long id);
}
