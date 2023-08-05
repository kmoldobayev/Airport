package kg.kuban.airport.enums;

public enum WearDegree {
    NEW("Новая"),
    GOOD("Хорошая"),
    MEDIUM("Средняя"),
    BAD("Плохая"),
    CRITICAL("Критическая");

    private final String description;

    WearDegree(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
