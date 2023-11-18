package restTemplate;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class CurrencyService {

    private static final String CB_RF_SITE = "cbr-xml-daily.ru";

    /**
     * Метод выводит на экран пользователя название и номинал валюты
     *
     * @return возвращает переведённые на русский название и номинал валюты
     */
    public static String currencyOut(String year, String month, String day, String country, RestTemplate restTemplate) {
        String[] splittingName = getCurrency(year, month, day, country, restTemplate).split(","); //разделяем валюту на компоненты: мусор([1][2][3]), название валюты([4]), номинал валюты([5])
        return splittingName[4].replace("\"Name\"", "Название валюты") //перевод на русский с помощью .replace()
                + splittingName[5].replace("\"Value\"", "Стоимость в рублях на " + new DateFormat(year, month, day)); //вывод названия валюты и номинала
    }

    /**
     * Метод обращается к API ЦБ РФ, находит нужную валюту, которая была выбрана пользователем
     *
     * @return возвращает валюту, которую выбрал пользователь
     */
    private static String getCurrency(String year, String month, String day, String country, RestTemplate restTemplate) {
        String url = "https://www." + CB_RF_SITE + "/archive/" + new DateFormat(year, month, day) + "/daily_json.js"; //создание url для обращения к API ЦБ РФ. Дата в формате yyyy/mm/dd
        String allText = Objects.requireNonNull(restTemplate.getForObject(url, String.class)); //получение текста с валютами
        String[] splitting = allText.split("\"Valute\": *"); //разделение текста на мусор и валюты. [0] - мусор, [1] - валюты
        String[] currencies = splitting[1].split("},"); //разделяем текст валют на массив по 1 валюте в каждой ячейке
        for (String currency : currencies) {
            if (currency.contains(country)) { //находим среди массива валют нужную ячейку
                return currency;
            }
        }
        throw new RuntimeException("Данной валюты нет в списке доступных валют.");
    }

    public static void isTransmittedDateAfterNow(LocalDate transmittedDate) {
        LocalDate nowDate = LocalDate.now();
        if (transmittedDate.isAfter(nowDate)) {
            throw new RuntimeException("Установленная дата не должна быть больше нынешней.");
        }
    }
}