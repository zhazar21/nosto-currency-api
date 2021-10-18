package tr.currency.api.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * ExchangeModel to use pojo model for currency calculation
 */
@Data
@AllArgsConstructor
public class ExchangeInputModel implements Serializable {

    private String fromCurrency;
    private BigDecimal inputMoney;
    private String toCurrency;

}
