package kg.kuban.airport.enums;

public enum FlightStatus {
    REGISTERED("Зарегистрированный"),      // Запланирован
    SELLING_TICKETS("Вылетел"),       // Вылетел
    SOLD_OUT("Приземлился"),        // Приземлился
    DEPARTURE_INITIATED("Вовремя"),        // ВОВРЕМЯ
    TECH_PREP_COMPLETE("Задержан"),        // ЗАДЕРЖАН
    CREW_PREP_CLIENT_CHECK("Отменен"),      // ОТМЕНЕН
    CREW_PREP_CLIENT_CHECKED("Перенаправлен"),       // ПЕРЕНАПРАВЛЕН
    CREW_PREP_CLIENTS_BRIEFING("В процессе"),     // В процессе
    CREW_PREP_CLIENTS_BRIEFED("В процессе"),     // В процессе
    CREW_PREP_CLIENTS_READY("В процессе"),     // В процессе
    CREW_READY("В процессе"),     // В процессе
    DEPARTURE_READY("В процессе"),     // В процессе
    DEPARTURE_CONFIRMED("В процессе"),     // В процессе
    FLIGHT_STARTED("В процессе"),     // В процессе
    FLIGHT_FOOD_DISTRIBUTION("В процессе"),     // В процессе
    FLIGHT_FOOD_DISTRIBUTED("В процессе");     // В процессе

    private String name;

    private FlightStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
