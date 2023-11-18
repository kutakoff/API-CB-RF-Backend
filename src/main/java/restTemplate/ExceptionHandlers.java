package restTemplate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(HttpClientErrorException.class)
    private ResponseEntity<String> handleHttpClientErrorException(HttpClientErrorException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ошибка. Курс ЦБ РФ на данную дату не установлен. Попробуйте другую дату, либо проверить: https://www.cbr.ru/currency_base/daily.");
    }

    @ExceptionHandler(NullPointerException.class)
    private ResponseEntity<String> handleHttpClientErrorException(NullPointerException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Введите все значения.");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    private ResponseEntity<String> handleHttpClientErrorException(HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Введите все значения.");
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<String> handleHttpClientErrorException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}