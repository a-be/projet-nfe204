package net.cnam.nfe204;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraficRepository extends CrudRepository<TraficEntry, String> {
}
