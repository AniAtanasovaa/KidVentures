package defence.app.model.enums;

public enum CategoryEnum {
    НАВЪН,
    ВЪТРЕ,
    ЖИВОТНИ;


    private final String displayName;

    CategoryEnum(String displayName) {
        this.displayName = displayName;
    }

    CategoryEnum() {
        this.displayName = null;
    }

    public String getDisplayName() {
        return displayName;
    }
}