package restTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static restTemplate.CurrencyService.currencyOut;

@RestController
@RequestMapping("/cbrf")
public class CurrencyController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Метод обрабатывает данные, которые юзер указал в Фронтенде, обратным ответом отправляет в фронт название и номинал валюты
     *
     * @param formDto обработка полученных данных из Фронтенда
     * @return отправляет в фронтенд название и номинал валюты
     */
    @PostMapping("/currency")
    @CrossOrigin
    public String handleRequest(@RequestBody FormDto formDto) {
        // Обработка полученных данных
        LocalDate date = formDto.getDate();
        String day = String.valueOf(date.getDayOfMonth()); //получаем день
        String month = String.valueOf(date.getMonthValue()); //получаем месяц
        String year = String.valueOf(date.getYear()); //получаем год
        String country = formDto.getComponent(); //получаем страну
        return currencyOut(year, month, day, country, restTemplate); //выводит на экран пользователя название и номинал валюты
    }
}