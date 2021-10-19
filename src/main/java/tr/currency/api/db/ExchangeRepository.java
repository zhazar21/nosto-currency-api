package tr.currency.api.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.currency.api.web.entity.ExchangeModel;

@Repository
public interface ExchangeRepository extends CrudRepository<ExchangeModel, Long> {

}
