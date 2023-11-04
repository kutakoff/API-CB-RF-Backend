package restTemplate;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(HttpClientErrorException.class)
    private String handleException(HttpClientErrorException e) {
        return "Ошибка. Курс ЦБ РФ на данную дату не установлен. Попробуйте другую дату, либо проверить: https://www.cbr.ru/currency_base/daily.";
    }
}