package defence.app.model.enums;

public enum CategoryEnum {
    НАВЪН,
    ВЪТРЕ,
    ЖИВОТНИ,
    DEFAULT_CATEGORY("ИЗВЪН КАТЕГОРИИТЕ");

    private final String displayName;

    CategoryEnum(String displayName) {
        this.displayName = displayName;
    }

    CategoryEnum() {
        this.displayName = null;  // или поставете стойност по подразбиране, която ви удовлетворява
    }

    public String getDisplayName() {
        return displayName;
    }
}