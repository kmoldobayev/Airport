package kg.kuban.airport.enums;

public enum AirplanePartStatus {
    OK("Исправный"),
    MAINTENANCE("Требуется тех обслуживание"),
    OUT_OF_ORDER("Неисправный");

    private String name;

    private AirplanePartStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
