package restTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import restTemplate.exceptions.InvalidInputException;
import restTemplate.exceptions.NotFoundCurrencyException;

@RestController
public class CurrencyController extends ExceptionHandlers {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "{year}/{month}/{day}/{country}", produces = "text/plain;charset=UTF-8")
    public String getCurrency(@PathVariable String year, @PathVariable String month, @PathVariable String day, @PathVariable String country) throws InvalidInputException, NotFoundCurrencyException {
        return new CurrencyService().callService(year, month, day, Country.valueOf(country.toUpperCase()), restTemplate);
    }
}