package tr.currency.api.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRatesApiModel implements Serializable {

    private String success;
    private Timestamp timestamp;
    private String base;
    private Date date;
    private Map<String, Double> rates;
}
