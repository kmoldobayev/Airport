package kg.kuban.airport.enums;

public enum AirplaneStatus {
    NEW("Новый"),            // новый (зарегистрирвоанный) самолет
    REPAIRED("На ремонте"),       // самолет находита на ремонте
    READY("Готов к вылету"),          // готовый (доступный) готов к вылету
    IN_FLIGHT("В полете"),      // самолет находится в полете
    ON_MAINTENANCE("На техобслуживании");  // самолет на техническом обслуживании

    private String name;

    private AirplaneStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
