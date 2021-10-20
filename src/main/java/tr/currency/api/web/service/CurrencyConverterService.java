package tr.currency.api.web.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tr.currency.api.web.entity.ExchangeModel;
import tr.currency.api.web.entity.ExchangeRatesApiModel;
import tr.currency.api.web.exception.CurrencyNotfoundException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

@Component
@AllArgsConstructor
public class CurrencyConverterService {

    public static final String EUR = "EUR";
    private static final Logger logger = LoggerFactory.getLogger(CurrencyConverterService.class);
    private final RestTemplate restTemplate;
    private final LogService logService;

    private final Environment env;

    public Map<String, Double> getCurrencies() {

        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        final HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = env.getProperty("currency.url");
        ResponseEntity<ExchangeRatesApiModel> response = restTemplate.exchange(url, HttpMethod.GET, entity, ExchangeRatesApiModel.class);

        if (!response.hasBody()) {
            throw new CurrencyNotfoundException("Connection problem!");
        }

        ExchangeRatesApiModel exchangeRatesApiModel = null;

        if (response.getStatusCode() == HttpStatus.OK) {
            exchangeRatesApiModel = response.getBody();
            return exchangeRatesApiModel.getRates();
        }

        throw new CurrencyNotfoundException("Connection problem!");
    }

    public Set<String> getCurrenciesTypes() {
        return getCurrencies().keySet();
    }

    public ExchangeModel calculateConvection(ExchangeModel model) {

        try {
            long startTime = System.nanoTime();
            model.setRequestTime(LocalDateTime.now());

            BigDecimal result = null;
            final BigDecimal receivedMoney = model.getInputMoney();

            if (model.getFromCurrency().equals(EUR)) {
                result = calculateForEuro(model, receivedMoney);
            } else {
                result = calculateForOthers(model, receivedMoney);
            }

            model.setResponseTime(LocalDateTime.now());
            model.setOutputMoney(result);
            logService.saveLog(model, result);

            logger.info(String.format(" Calculation time %s ms", (System.nanoTime() - startTime) / 1000000));

        } catch (Exception e) {
            throw new CurrencyNotfoundException("Currency is not supported!");
        }
        return model;
    }

    private BigDecimal calculateForOthers(ExchangeModel model, BigDecimal receivedMoney) {

        final Double againtsEuro = 1 / getCurrencies().get(model.getFromCurrency());
        final Double convertedEuro = 1 / getCurrencies().get(model.getToCurrency());

        final BigDecimal multiplyMoney = receivedMoney.multiply(BigDecimal.valueOf(againtsEuro));
        return multiplyMoney.divide(BigDecimal.valueOf(convertedEuro), 5, RoundingMode.UP);
    }

    private BigDecimal calculateForEuro(ExchangeModel model, BigDecimal receivedMoney) {
        final BigDecimal result = receivedMoney.multiply(BigDecimal.valueOf(getCurrencies().get(model.getToCurrency())));
        return result.setScale(5, RoundingMode.UP);
    }


}
