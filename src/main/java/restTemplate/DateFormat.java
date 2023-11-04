package restTemplate;

public record DateFormat(String year, String month, String day) {

    @Override
    public String toString() {
        return year + "/" + month + "/" + day;
    }
}