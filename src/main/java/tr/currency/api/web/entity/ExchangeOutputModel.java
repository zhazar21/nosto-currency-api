package tr.currency.api.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * ExchangeModel to use pojo model for currency calculation
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeOutputModel implements Serializable {

    private String fromCurrency;
    private BigDecimal inputMoney;

    private String toCurrency;
    private BigDecimal outputMoney;

    private String exchangeRate;

    private String message;

    private LocalDateTime calculationTime;

}
