package kg.kuban.airport.enums;

public enum InspectionStatus {
    SCHEDULED("Запланирован"),
    IN_PROGRESS("Выполняется"),
    COMPLETED("Завершен"),
    CANCELED("Отменен"),
    FAILED("Не удалось");

    private String displayName;

    private InspectionStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
