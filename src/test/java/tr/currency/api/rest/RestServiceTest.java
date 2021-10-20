package tr.currency.api.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.web.WebAppConfiguration;
import tr.currency.api.web.service.CurrencyConverterService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * RestServiceTest
 */
@WebAppConfiguration
@SpringBootTest(classes = RestService.class)
class RestServiceTest {

    @MockBean
    private CurrencyConverterService currencyConverterService;

    @MockBean
    private RestService restService;

    @BeforeEach
    void setUp() {
        final Map<String, String> testitModel = new HashMap<>();
        testitModel.put("EUR", "test");

        final Map<String, Double> model = new HashMap<>();
        model.put("EUR", (double) 15);
        model.put("USD", (double) 10);

        Mockito.when(currencyConverterService.getCurrencies())
                .thenReturn(model);

        Mockito.when(restService.getCurrencies())
                .thenReturn(new ResponseEntity<>(model, HttpStatus.OK));

        Mockito.when(restService.getCurrencyTypes())
                .thenReturn(model.keySet());
    }

    @Test
    @DisplayName("Test for all currency types")
    void getCurrenciesTypes() {
        Set<String> result = restService.getCurrencyTypes();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("Test for all currencies")
    void getCurrencies() {
        ResponseEntity<Map<String, Double>> result = restService.getCurrencies();
        assertThat(result.getBody()).isNotEmpty();
    }
}
