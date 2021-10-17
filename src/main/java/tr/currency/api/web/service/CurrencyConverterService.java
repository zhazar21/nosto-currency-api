package tr.currency.api.web.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tr.currency.api.web.entity.ExchangeModel;
import tr.currency.api.web.entity.ExchangeRatesApiModel;
import tr.currency.api.web.exception.CurrencyException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
public class CurrencyConverterService {

    public static final String EUR = "EUR";

    private final Environment env;


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Cacheable("getCurrencies")
    public Map<String, Double> getCurrencies() throws CurrencyException {
        RestTemplate restTemplate =new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        final HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ExchangeRatesApiModel> response = restTemplate.exchange(env.getProperty("currency.url"), HttpMethod.GET, entity, ExchangeRatesApiModel.class);

        ExchangeRatesApiModel exchangeRatesApiModel = null;
        if (response.getStatusCode() == HttpStatus.OK) {
            exchangeRatesApiModel = response.getBody();
        }
        // throw new CurrencyException("Error on getin exceptions",e);
        return exchangeRatesApiModel.getRates();
    }

    @Cacheable("getCurrenciesTypes")
    public Set<String> getCurrenciesTypes() throws CurrencyException {
        return getCurrencies().keySet();
    }

    public ExchangeModel convertCurrency(ExchangeModel model) throws CurrencyException {
        BigDecimal result = null;

        final String gelenParaBirimi = model.getFromCurrency();
        final String donusturulecekParaBirimi = model.getToCurrency();

        final Double birimin_euro_karsiligi = 1/getCurrencies().get(model.getFromCurrency());
        final Double donusturulennin_euro_karsiligi = 1/getCurrencies().get(model.getToCurrency());

        final BigDecimal receivedMoney = new BigDecimal(String.valueOf(model.getInputMoney()));
        final BigDecimal girilen_paranın_euro_karsiligi = receivedMoney.multiply(new BigDecimal(birimin_euro_karsiligi));

        if (model.getFromCurrency().equals(EUR)) {
            result= receivedMoney.multiply(girilen_paranın_euro_karsiligi.setScale(5, RoundingMode.FLOOR));
        }else {
            BigDecimal donusum = receivedMoney.multiply(new BigDecimal(birimin_euro_karsiligi));
            result = donusum.divide(new BigDecimal(donusturulennin_euro_karsiligi), 5, RoundingMode.FLOOR);
        }

        model.setOutputMoney(result);

        return model;
    }


}
