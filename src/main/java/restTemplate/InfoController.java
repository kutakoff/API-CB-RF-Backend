package restTemplate;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @RequestMapping(value = "/help", produces = "text/plain;charset=UTF-8")
    private String getInfo() {
        return """
                Чтобы начать пользоваться сайтом, вам нужно вбить в поисковую строку: http://localhost:8080/год/месяц/число/кодстраны в формате yyyy/mm/dd/код.\s
                Вот всевозможные коды стран, которые вы можете ввести:\s
                AUD - Австралийский доллар
                AZN - Азербайджанский манат
                GBP - Фунт стерлингов Соединенного королевства
                AMD - Армянских драмов
                BYN - Белорусский рубль
                BGN - Болгарский лев
                BRL - Бразильскийреал
                HUF - Венгерских форинтов
                HKD - Гонконгских долларов
                DKK - Датских крон
                USD - Доллар США
                EUR - Евро
                INR - Индийских рупий
                KZT - Казахстанских тенге
                CAD - Канадский доллар
                KGS - Киргизских сомов
                CNY - Китайских юаней
                MDL - Молдавских леев
                NOK - Норвежских крон
                PLN - Польский злотый
                RON - Румынский лей
                XDR - СДР (специальные права заимствования)
                SGD - Сингапурский доллар
                TJS - Таджикских сомони
                TRY - Турецкая лира
                TMT - Новый туркменский манат
                UZS - Узбекских сумов
                UAH - Украинских гривен
                CZK - Чешских крон
                SEK - Шведских крон
                CHF - Швейцарский франк
                ZAR - Южноафриканских рэндов
                KRW - Вон Республики Корея
                JPY - Японских иен""";
    }
}
