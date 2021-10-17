package tr.currency.api.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * ExchangeModel to use pojo model for currency calculation
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeModel implements Serializable {

    private String fromCurrency;
    private BigDecimal inputMoney;

    private String toCurrency;
    private BigDecimal outputMoney;

    private String exchangeRate;

}
