package company.repository;

import company.entity.StatusPerson;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusPersonRepository extends CrudRepository<StatusPerson, Long> {

}

