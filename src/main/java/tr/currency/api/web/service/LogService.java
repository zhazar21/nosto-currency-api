package tr.currency.api.web.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import tr.currency.api.db.ExchangeRepository;
import tr.currency.api.db.LogRepository;
import tr.currency.api.web.entity.CurrencyLog;
import tr.currency.api.web.entity.ExchangeModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class LogService {

    private final ExchangeRepository exchangeRepository;

    private final LogRepository logRepository;

    void saveLog(ExchangeModel model, BigDecimal result) {

        model.setOutputMoney(result);

        final CurrencyLog input = CurrencyLog.builder().createDate(LocalDateTime.now()).exchangeModel(model).build();

        exchangeRepository.save(model);
        logRepository.save(input);

    }
}
