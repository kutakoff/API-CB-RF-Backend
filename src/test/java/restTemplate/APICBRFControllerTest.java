package restTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;


@WebMvcTest(CurrencyController.class)
@AutoConfigureMockMvc
public class APICBRFControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RestTemplate restTemplate;

    private String ALL_TEXT_CONST =
            "\"Date\": \"2023-11-16T11:30:00+03:00\"," + "\n" +
            "\"PreviousDate\": \"2023-11-15T11:30:00+03:00\"," + "\n" +
            "\"PreviousURL\": \"/www.cbr-xml-daily.ru/archive/2023/11/15/daily_json.js\"," + "\n" +
            "\"Timestamp\": \"2023-11-15T20:00:00+03:00\"," + "\n" +
            "\"Valute\": {" + "\n" +
        "\"AUD\": {" + "\n" +
            "\"ID\": \"R01010\"," + "\n" +
                    "\"NumCode\": \"036\"," + "\n" +
                    "\"CharCode\": \"AUD\"," + "\n" +
                    "\"Nominal\": 1," + "\n" +
                    "\"Name\": \"Австралийский доллар\"," + "\n" +
                    "\"Value\": 58.1467," + "\n" +
                    "\"Previous\": 58.149" + "\n" +
        "}" + "\n" +
    "}" + "\n" +
"}";

    @Test
    public void isOkWithCorrectData() throws Exception {
        doReturn(ALL_TEXT_CONST).when(restTemplate).getForObject(anyString(), any());
        mockMvc.perform(MockMvcRequestBuilders.post("/cbrf/currency")
                        .content(objectMapper.writeValueAsString(new FormDto(LocalDate.now(), String.valueOf(Country.AUD))))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void isClientError400WithFirstNullArgumentData() throws Exception {
        doReturn(ALL_TEXT_CONST).when(restTemplate).getForObject(anyString(), any());
        mockMvc.perform(MockMvcRequestBuilders.post("/cbrf/currency")
                        .content(objectMapper.writeValueAsString(new FormDto(null, String.valueOf(Country.AUD))))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Введите все значения."));
    }

    @Test
    public void isClientError400WithSecondNullArgumentData() throws Exception {
        doReturn(ALL_TEXT_CONST).when(restTemplate).getForObject(anyString(), any());
        mockMvc.perform(MockMvcRequestBuilders.post("/cbrf/currency")
                        .content(objectMapper.writeValueAsString(new FormDto(LocalDate.now(), null)))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Введите все значения."));
    }

    @Test
    public void isClientError400WithNullArgument() throws Exception {
        doReturn(ALL_TEXT_CONST).when(restTemplate).getForObject(anyString(), any());
        mockMvc.perform(MockMvcRequestBuilders.post("/cbrf/currency")
                        .content(objectMapper.writeValueAsString(null))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Введите все значения."));
    }

    @Test
    public void isClientError404WithUnavailableCurrency() throws Exception {
        doReturn(ALL_TEXT_CONST).when(restTemplate).getForObject(anyString(), any());
        mockMvc.perform(MockMvcRequestBuilders.post("/cbrf/currency")
                        .content(objectMapper.writeValueAsString(new FormDto(LocalDate.now(), "NEC")))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Данной валюты нет в списке доступных валют."));
    }

    @Test
    public void isClientError404WithUnavailableDate() throws Exception {
        LocalDate localDate = LocalDate.of(2143, 11, 25);
        doReturn(ALL_TEXT_CONST).when(restTemplate).getForObject(anyString(), any());
        mockMvc.perform(MockMvcRequestBuilders.post("/cbrf/currency")
                        .content(objectMapper.writeValueAsString(new FormDto(localDate, String.valueOf(Country.AUD))))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Установленная дата не должна быть больше нынешней."));
    }

    @Test
    public void isClientError400WithEmptyArgument() throws Exception {
        doReturn(ALL_TEXT_CONST).when(restTemplate).getForObject(anyString(), any());
        mockMvc.perform(MockMvcRequestBuilders.post("/cbrf/currency")
                        .content(objectMapper.writeValueAsString(new FormDto()))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Введите все значения."));
    }

    @Test
    public void isClientErrorWithHttpClientErrorException() throws Exception {
        doThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND)).when(restTemplate).getForObject(anyString(), any());
        mockMvc.perform(MockMvcRequestBuilders.post("/cbrf/currency")
                        .content(objectMapper.writeValueAsString(new FormDto(LocalDate.now(), String.valueOf(Country.AUD))))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void isClientErrorWithNullPointerException() throws Exception {
        doThrow(new NullPointerException()).when(restTemplate).getForObject(anyString(), any());
        mockMvc.perform(MockMvcRequestBuilders.post("/cbrf/currency")
                        .content(objectMapper.writeValueAsString(new FormDto(LocalDate.now(), String.valueOf(Country.AUD))))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void isClientErrorWithArrayStoreException() throws Exception {
        doThrow(new ArrayStoreException()).when(restTemplate).getForObject(anyString(), any());
        mockMvc.perform(MockMvcRequestBuilders.post("/cbrf/currency")
                        .content(objectMapper.writeValueAsString(new FormDto(LocalDate.now(), String.valueOf(Country.AUD))))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
