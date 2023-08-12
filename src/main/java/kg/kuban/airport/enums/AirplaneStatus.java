package kg.kuban.airport.enums;

public enum AirplaneStatus {
    NEEDS_INSPECTION("Требуется инспекция"),    // самолет требует инспекции
    ON_INSPECTION("На инспекции"),              // самолет на инспекции
    SERVICEABLE("На тех осмотре"),              // самолет на тех осмотре
    ON_REPAIRS("На ремонте"),                                               // самолет находится на ремонте
    REGISTRATION_PENDING_CONFIRMATION("Ожидает подтверждение регистрации"), // самолет ожидает подтверждение регистрации

    AVAILABLE("Доступен"),                      // самолет доступный

    ON_REFUELING("На заправке"),                // самолет на заправке
    REFUELED("Заправлен"),                      // самолет заправлен
    IN_AIR("В полете");                         // самолет находится в полете (в воздухе)

    private String name;

    private AirplaneStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
