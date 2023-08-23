package kg.kuban.airport.enums;

public enum FlightStatus {
    REGISTERED("Зарегистрирован"),
    CREW_MEMBERS_REGISTERED("Члены экапижа зарегистрированы"),
    SELLING_TICKETS("Продажа билетов"),
    SOLD_OUT("Билеты распроданы"),
    DEPARTURE_INITIATED("Инициирование отправки рейса"),
    TECH_PREP_COMPLETE("Завершена техническая подготовка (Заправка самолета)"),
    CUSTOMER_CHECK("Проверка клиента к рейсу"),
    CUSTOMERS_CHECKED("Клиенты првоерены"),
    CUSTOMERS_BRIEFING("Инструктаж клиентов"),
    CUSTOMERS_BRIEFED("Клиенты проинструктированы"),
    CUSTOMERS_READY("Клиенты готовы"),
    CREW_READY("Экипаж готов"),
    DEPARTURE_READY("Вылет готов"),
    DEPARTURE_CONFIRMED("Вылет подтвержден"),
    FLIGHT_STARTED("Рейс начат"),
    FLIGHT_FOOD_DISTRIBUTION("Раздача еды на борту"),
    FLIGHT_FOOD_DISTRIBUTED("Еда выдана"),
    LANDING_REQUESTED("Запрос на приземление"),
    LANDING_PENDING_CONFIRMATION("Ожидание подтверждения на приземление"),
    LANDING_CONFIRMED("Приземление подтверждено"),
    ARRIVED("Прибыл");

    private String name;

    private FlightStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
