package restTemplate;

import java.time.LocalDate;

/**
 * Класс предназначен для обработки данных из Frontend в Backend
 */
public class FormDto {
    private LocalDate date;
    private String country;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public FormDto() {
    }

    public FormDto(LocalDate date, String component) {
        this.date = date;
        this.country = component;
    }
}
