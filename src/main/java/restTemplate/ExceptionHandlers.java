package restTemplate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import restTemplate.exceptions.InvalidInputException;
import restTemplate.exceptions.NotFoundCurrencyException;

public class ExceptionHandlers {

    @ExceptionHandler(InvalidInputException.class)
    private ResponseEntity<Object> handleException(InvalidInputException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка в верности указанной даты. Требуемый формат: yyyy/mm/dd. Для дополнительной помощи введите в строку браузера http://localhost:8080/help");
    }

    @ExceptionHandler(NumberFormatException.class)
    private ResponseEntity<Object> handleException(NumberFormatException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ошибка в верности указанной даты. Вводите только цифры. Требуемый формат: yyyy/mm/dd. Для дополнительной помощи введите в строку браузера http://localhost:8080/help");
    }

    @ExceptionHandler({NotFoundCurrencyException.class, IllegalArgumentException.class})
    private ResponseEntity<Object> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ошибка. Валюты такой страны нет. Введите http://localhost:8080/help в строку браузера для уточнения существующих валют.");
    }

    @ExceptionHandler(HttpClientErrorException.class)
    private ResponseEntity<Object> handleException(HttpClientErrorException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Курс ЦБ РФ на данную дату не установлен. Проверить: https://www.cbr.ru/currency_base/daily. Для дополнительной помощи введите в строку браузера http://localhost:8080/help");
    }
}