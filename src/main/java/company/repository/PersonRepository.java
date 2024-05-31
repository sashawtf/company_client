package company.repository;

import company.entity.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    boolean existsByStatusId(Long id);

    boolean existsByVerietyId(Long id);
}