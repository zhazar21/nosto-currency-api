package tr.currency.api.web.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tr.currency.api.web.entity.ExchangeModel;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CurrencyConverterServiceTest {

    @MockBean
    private CurrencyConverterService currencyConverterService;

    @BeforeEach
    void setUp() {

        final Map<String, Double> model = new HashMap<>();
        model.put("EUR", (double) 15);
        model.put("USD", (double) 10);

        Mockito.when(currencyConverterService.getCurrencies())
                .thenReturn(model);
    }

    @Test
    @DisplayName("Test for get all currencies")
    void getCurrenciesTest() {
        Map<String, Double> result = currencyConverterService.getCurrencies();
        assertThat(result).isNotEmpty();
    }


    @Test
    @DisplayName("Test for convert currency")
    void convertCurrencyTest() {
        final ExchangeModel exchangeModel = ExchangeModel.builder()
                .inputMoney(BigDecimal.ONE)
                .outputMoney(BigDecimal.TEN)
                .fromCurrency("EUR")
                .toCurrency("USD")
                .exchangeRate("3").build();

        currencyConverterService.convertCurrency(exchangeModel);
        boolean result = exchangeModel.getOutputMoney().equals(new BigDecimal(3));
        assertThat(result);
    }
}
