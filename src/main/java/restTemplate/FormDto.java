package restTemplate;

import java.time.LocalDate;

/**
 * Класс предназначен для обработки данных из Frontend в Backend
 */
public class FormDto {
    private LocalDate date;
    private String component;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }
}
