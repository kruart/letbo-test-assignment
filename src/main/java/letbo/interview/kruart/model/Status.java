package letbo.interview.kruart.model;

public enum Status {
    NOT_STARTED("not started"),
    STARTED("started"),
    FINISHED("finished");

    private final String value;
    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
