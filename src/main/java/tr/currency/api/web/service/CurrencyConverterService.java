package tr.currency.api.web.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
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

@Service
@AllArgsConstructor
public class CurrencyConverterService {

    public static final String EUR = "EUR";

    private final Environment env;
    private final LogService logService;

    private static final Logger logger = LoggerFactory.getLogger(CurrencyConverterService.class);

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public Map<String, Double> getCurrencies() {
        RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        final HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ExchangeRatesApiModel> response = restTemplate.exchange(env.getProperty("currency.url"), HttpMethod.GET, entity, ExchangeRatesApiModel.class);

        ExchangeRatesApiModel exchangeRatesApiModel = null;
        if (response.hasBody() && response.getStatusCode() == HttpStatus.OK) {
            exchangeRatesApiModel = response.getBody();
        }
        return exchangeRatesApiModel.getRates();
    }

    public Set<String> getCurrenciesTypes() {
        return getCurrencies().keySet();
    }

    public ExchangeModel convertCurrency(ExchangeModel model) {

        try {

            long startTime = System.nanoTime();
            model.setRequestTime(LocalDateTime.now());
            BigDecimal result = null;

            final Double euro_convertion = 1 / getCurrencies().get(model.getFromCurrency());
            final Double convertedEuro = 1 / getCurrencies().get(model.getToCurrency());

            final BigDecimal receivedMoney = model.getInputMoney();
            final BigDecimal inputMoneyToEuro = receivedMoney.multiply(new BigDecimal(euro_convertion));

            if (model.getFromCurrency().equals(EUR)) {
                result = receivedMoney.multiply(inputMoneyToEuro.setScale(5, RoundingMode.FLOOR));
            } else {
                BigDecimal donusum = receivedMoney.multiply(BigDecimal.valueOf(euro_convertion));
                result = donusum.divide(new BigDecimal(convertedEuro), 5, RoundingMode.FLOOR);
            }

            model.setResponseTime(LocalDateTime.now());
            logService.saveLog(model, result);

            logger.info(" Calculation time " + (System.nanoTime() - startTime) / 1000000 + " ms");
        } catch (Exception e) {
            throw new CurrencyNotfoundException("Currency is not supported!");
        }
        return model;
    }


}
