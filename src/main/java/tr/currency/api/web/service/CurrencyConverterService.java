package tr.currency.api.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
public class CurrencyConverterService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyConverterService.class);

    public static final String EUR = "EUR";
    private final RestTemplate restTemplate;
    private final LogService logService;
    private final String url;

    public CurrencyConverterService(LogService logService, RestTemplate restTemplate) {
        this.logService = logService;
        this.url = getUrl();
        this.restTemplate = restTemplate;
    }

    @Value("${currency.url}")
    public String getUrl() {
        return url;
    }

    public Map<String, Double> getCurrencies() {

        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        final HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ExchangeRatesApiModel> response = restTemplate.exchange(getUrl(), HttpMethod.GET, entity, ExchangeRatesApiModel.class);

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

            final Double againtsEuro = 1 / getCurrencies().get(model.getFromCurrency());
            final Double convertedEuro = 1 / getCurrencies().get(model.getToCurrency());

            final BigDecimal receivedMoney = model.getInputMoney();
            final BigDecimal inputMoneyToEuro = receivedMoney.multiply(BigDecimal.valueOf(againtsEuro));

            if (model.getFromCurrency().equals(EUR)) {
                result = receivedMoney.multiply(inputMoneyToEuro.setScale(5, RoundingMode.FLOOR));
            } else {
                BigDecimal donusum = receivedMoney.multiply(BigDecimal.valueOf(againtsEuro));
                result = donusum.divide(BigDecimal.valueOf(convertedEuro), 5, RoundingMode.FLOOR);
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


}
