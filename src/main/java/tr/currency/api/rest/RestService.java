package tr.currency.api.rest;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.currency.api.web.entity.ExchangeInputModel;
import tr.currency.api.web.entity.ExchangeModel;
import tr.currency.api.web.entity.ExchangeOutputModel;
import tr.currency.api.web.entity.ModelMapper;
import tr.currency.api.web.service.CurrencyConverterService;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class RestService {

    private final CurrencyConverterService currencyConverterService;
    private final MessageSource messageSource;

    /**
     * List currencies rest endpoint
     *
     * @return Set of keys
     */
    @GetMapping("/currencyTypes")
    public Set<String> getCurrencyTypes() {
        return currencyConverterService.getCurrencies().keySet();
    }


    /**
     * List currencies rest endpoint
     *
     * @return Set of keys
     */
    @PostMapping("/convertCurrencies")
    public ResponseEntity<ExchangeOutputModel> convertCurrency(@RequestBody ExchangeInputModel exchangeInputModel) {
        final ExchangeModel model = ModelMapper.INSTANCE.convert(exchangeInputModel);
        final ExchangeModel response = currencyConverterService.convertCurrency(model);
        final String message = messageSource.getMessage("convert.message", null, LocaleContextHolder.getLocale());
        final ExchangeOutputModel exchangeOutputModel = ModelMapper.INSTANCE.convert(response);
        exchangeOutputModel.setMessage(message);
        return new ResponseEntity<>(exchangeOutputModel, HttpStatus.OK);
    }

    /**
     * List currencies with available prices rest endpoint
     *
     * @return Set of key/values
     */
    @GetMapping("/currencies")
    public ResponseEntity<Map<String, Double>> getCurrencies() {
        return new ResponseEntity<>(currencyConverterService.getCurrencies(), HttpStatus.OK);
    }



}
