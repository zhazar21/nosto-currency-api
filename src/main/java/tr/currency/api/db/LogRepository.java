package tr.currency.api.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.currency.api.web.entity.CurrencyLog;

@Repository
public interface LogRepository extends CrudRepository<CurrencyLog, Long> {

}
