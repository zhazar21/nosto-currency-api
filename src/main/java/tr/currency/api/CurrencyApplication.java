package tr.currency.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Spring boot application start point
 */
@SpringBootApplication
@EnableCaching
public class CurrencyApplication {

    /**
     * start point
     *
     * @param args system arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(CurrencyApplication.class, args);
    }
}
