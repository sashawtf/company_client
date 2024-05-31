package company.repository;

import company.entity.VerietyPerson;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerietyPersonRepository extends CrudRepository<VerietyPerson, Long> {

}
