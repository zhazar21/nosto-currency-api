package tr.currency.api.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * ExchangeModel to use pojo model for currency calculation
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String fromCurrency;
    private BigDecimal inputMoney;

    private String toCurrency;
    private BigDecimal outputMoney;

    private String exchangeRate;

}
