package tr.currency.api.rest;

import static org.assertj.core.api.Assertions.assertThat;

import tr.currency.api.web.exception.CurrencyException;
import tr.currency.api.web.service.CurrencyConverterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * RestServiceTest
 */
//@SpringBootTest
class RestServiceTest {

//    @MockBean
    private RestService restService;

//    @MockBean
    private CurrencyConverterService currencyConverterService;

//    @BeforeEach
    void setUp() throws CurrencyException {

        final Map<String, String> testitModel = new HashMap<>();
        testitModel.put("EUR", "test");

        final Map<String, Double> model = new HashMap<String, Double>();
        model.put("EUR", (double) 15);
        model.put("USD", (double) 10);

        Mockito.when(currencyConverterService.getCurrencies())
                .thenReturn(model);

//        Mockito.when(restService.getCurrencies())
//                .thenReturn(model);

        Mockito.when(restService.getCurrencyTypes())
                .thenReturn(model.keySet());

    }

//    @Test
//    @DisplayName("Test for all currency types")
    void getCurrenciesTypes() throws CurrencyException {
        Set<String> result =restService.getCurrencyTypes();
        assertThat(result).isNotEmpty();
    }

//    @Test
//    @DisplayName("Test for all currencies")
    void getCurrencies() throws CurrencyException {
//        Map<String, Double> result =restService.getCurrencies();
//        assertThat(result).isNotEmpty();
    }

}
