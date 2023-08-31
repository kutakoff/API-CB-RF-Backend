package restTemplate;

public record Date(String year, String month, String day) {

    @Override
    public String toString() {
        return year + "/" + month + "/" + day;
    }
}