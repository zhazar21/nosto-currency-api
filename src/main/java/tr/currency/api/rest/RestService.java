package tr.currency.api.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.currency.api.web.exception.CurrencyException;
import tr.currency.api.web.service.CurrencyConverterService;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class RestService {

    private final CurrencyConverterService currencyConverterService;

    /**
     * List currencies rest endpoint
     *
     * @return Set of keys
     */
    @GetMapping("/currencyTypes")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public Set<String> getCurrencyTypes() throws CurrencyException {
        return currencyConverterService.getCurrencies().keySet();
    }

    /**
     * List currencies with available prices rest endpoint
     *
     * @return Set of key/values
     */
    @GetMapping("/currencies")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public ResponseEntity<Map<String, Double>> getCurrencies() throws CurrencyException {
        return new ResponseEntity<>(currencyConverterService.getCurrencies(), HttpStatus.OK);
    }



}
