package restTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static restTemplate.CurrencyService.*;

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
        isTransmittedDateAfterNow(date); //проверка на то, что установленная дата не позднее нынешней
        String day = String.valueOf(date.getDayOfMonth());
        String month = String.valueOf(date.getMonthValue());
        String year = String.valueOf(date.getYear());
        String country = formDto.getCountry(); //получаем страну
        return currencyOut(year, month, day, country, restTemplate); //выводит на экран пользователя название и номинал валюты
    }
}