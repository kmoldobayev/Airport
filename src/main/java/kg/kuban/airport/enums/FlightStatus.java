package kg.kuban.airport.enums;

public enum FlightStatus {
    SCHEDULED("Запланирован"),      // Запланирован
    DEPARTED("Вылетел"),       // Вылетел
    ARRIVED("Приземлился"),        // Приземлился
    ON_TIME("Вовремя"),        // ВОВРЕМЯ
    DELAYED("Задержан"),        // ЗАДЕРЖАН
    CANCELLED("Отменен"),      // ОТМЕНЕН
    DIVERTED("Перенаправлен"),       // ПЕРЕНАПРАВЛЕН
    IN_PROGRESS("В процессе");     // В процессе
    private String name;

    private FlightStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
