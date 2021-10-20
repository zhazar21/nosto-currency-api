package tr.currency.api.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import tr.currency.api.web.entity.ExchangeModel;
import tr.currency.api.web.service.CurrencyConverterService;

/**
 * IndexController
 */
@Controller
@AllArgsConstructor
public class IndexController {

    private final CurrencyConverterService currencyConverterService;

    private final MessageSource messageSource;
    /**
     * Index endpoint to show the index page
     *
     * @param model Spring's view model
     * @return view name
     */
    @GetMapping({"/", "/index"})
    public String index(Model model) {

        final String message = messageSource.getMessage("convert.message", null, LocaleContextHolder.getLocale());

        model.addAttribute("currenciestypes", currencyConverterService.getCurrenciesTypes());
        model.addAttribute("title", "Currency Conversion");
        model.addAttribute("welcome", message);
        model.addAttribute("applicationTitle", "Check24 Currency Converter");
        model.addAttribute("exchangeModel", ExchangeModel.builder().build());
        model.addAttribute("calculationtime", null);
        return "index";
    }

    /**
     * convert endpoint to show the index page after currency calculation
     *
     * @param model Spring's view model
     * @return view exchangeModel
     */
    @PostMapping(value = "/convert")
    public String add(@ModelAttribute("exchangeModel") ExchangeModel exchangeModel, Model model) {
        final String message = messageSource.getMessage("convert.message", null, LocaleContextHolder.getLocale());
        long startTime = System.nanoTime();
        exchangeModel = currencyConverterService.calculateConvection(exchangeModel);
        model.addAttribute("currenciestypes", currencyConverterService.getCurrenciesTypes());
        model.addAttribute("title", "Currency Conversion");
        model.addAttribute("welcome", message);
        model.addAttribute("applicationTitle", "Check24 Currency Converter");
        model.addAttribute("exchangeModel", exchangeModel);
        String calculationTime = "Calculation time " + (System.nanoTime() - startTime) / 1000000 + " ms";
        model.addAttribute("calculationtime", calculationTime);
        return "index";
    }

}
