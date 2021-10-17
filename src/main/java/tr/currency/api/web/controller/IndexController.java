package tr.currency.api.web.controller;

import tr.currency.api.web.entity.ExchangeModel;
import tr.currency.api.web.exception.CurrencyException;
import tr.currency.api.web.service.CurrencyConverterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * IndexController
 */
@Controller
@AllArgsConstructor
public class IndexController {

    private final CurrencyConverterService currencyConverterService;

    /**
     * Index endpoint to show the index page
     *
     * @param model Spring's view model
     * @return view name
     */
    @GetMapping({"/", "/index"})
    public String index(Model model) throws CurrencyException {

        model.addAttribute("currenciestypes", currencyConverterService.getCurrenciesTypes());
        model.addAttribute("title", "Currency Conversion");
        model.addAttribute("welcome", "Currency Conversion");
        model.addAttribute("applicationTitle", "Check24 Currency Converter");
        model.addAttribute("exchangeModel", ExchangeModel.builder().build());

        return "index";
    }

    /**
     * convert endpoint to show the index page after currency calculation
     *
     * @param model Spring's view model
     * @return view exchangeModel
     */
    @RequestMapping(value = "/convert", method = RequestMethod.POST)
    public String add(@ModelAttribute("exchangeModel") ExchangeModel exchangeModel, Model model) throws CurrencyException {

        exchangeModel = currencyConverterService.convertCurrency(exchangeModel);
        model.addAttribute("currenciestypes", currencyConverterService.getCurrenciesTypes());
        model.addAttribute("title", "Currency Conversion");
        model.addAttribute("welcome", "Currency Conversion");
        model.addAttribute("applicationTitle", "Check24 Currency Converter");
        model.addAttribute("exchangeModel", exchangeModel);

        return "index";
    }

}
